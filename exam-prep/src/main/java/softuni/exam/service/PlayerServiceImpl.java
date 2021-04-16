package softuni.exam.service;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PlayerSeedDtoGson;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.ValidatorUtil;


import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {


    private final PlayerRepository playerRepository;
    private final TeamService teamService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamService teamService, PictureService pictureService, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
    }

    @Override
    public String importPlayers() throws FileNotFoundException {
        String path = "src/main/resources/files/json/players.json";
        StringBuilder sb = new StringBuilder();

        PlayerSeedDtoGson[] playerSeedDtoGsons = this.gson.fromJson(new FileReader(path),
                PlayerSeedDtoGson[].class);

        Arrays.stream(playerSeedDtoGsons).forEach(playerSeedDtoGson -> {
            if (this.validatorUtil.isValid(playerSeedDtoGson)) {
                if (this.playerRepository.findByFirstNameAndLastName(playerSeedDtoGson.getFirstName(),
                        playerSeedDtoGson.getLastName()) == null) {

                    Player player = this.modelMapper.map(playerSeedDtoGson, Player.class);

                    Team team = this.teamService.getTeamByName(playerSeedDtoGson
                            .getTeam()
                            .getName());

                    Picture picture = this.pictureService.getPictureByUrl(playerSeedDtoGson
                            .getPicture()
                            .getUrl());

                    player.setTeam(team);
                    player.setPicture(picture);

                    this.playerRepository.saveAndFlush(player);
                    sb.append(String.format("%s %s successfully imported player",
                            player.getFirstName(), player.getLastName())).append(System.lineSeparator());

                } else {
                    sb.append("Already in DB").append(System.lineSeparator());
                }

            } else {
                sb.append("Invalid player").append(System.lineSeparator());
            }
        });

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        String path = "src/main/resources/files/json/players.json";
        return String.join("", Files.readAllLines(Path.of(path)));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();
        this.playerRepository.findAllBySalaryIsGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000))
                .forEach(player -> {
                    sb.append(String.format("Player name: %s %s%n Player number: %d%n Salary: %.2f%n Team: %s%n",
                            player.getFirstName(), player.getLastName(), player.getNumber(), player.getSalary(),
                            player.getTeam().getName()));
                    sb.append(System.lineSeparator());
                });
        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team: North Hub").append(System.lineSeparator());
        this.playerRepository.findAllByTeamName("North Hub")
                .forEach(player -> {
                    sb.append(String.format("Player name: %s %s%n Player position: %s%n Player number %d%n ",
                            player.getFirstName(), player.getLastName(), player.getPosition(), player.getNumber()));
                    sb.append(System.lineSeparator());
                });

        return sb.toString();
    }


}
