package softuni.exam.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.TeamSeedRootDtos;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final PictureRepository pictureRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureService pictureService, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validatorUtil, PictureRepository pictureRepository) {
        this.teamRepository = teamRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.pictureRepository = pictureRepository;
    }

    @Override

    public String importTeams() throws JAXBException, FileNotFoundException {
        String path = "src/main/resources/files/xml/teams.xml";
        StringBuilder sb = new StringBuilder();

        TeamSeedRootDtos teamSeedRootDtos =
                this.xmlParser.parseXml(TeamSeedRootDtos.class, path);

        teamSeedRootDtos
                .getTeams()
                .forEach(teamSeedDtos -> {
                    if (this.validatorUtil.isValid(teamSeedDtos)) {
                        if (this.pictureRepository.findByUrl(teamSeedDtos.getPicture().getUrl()) != null) {
                            if (this.pictureService.getPictureByUrl(teamSeedDtos.getPicture().getUrl()) == null) {
                                Team team = this.modelMapper.map(teamSeedDtos, Team.class);

                                Picture pictureByUrl = this.pictureService.getPictureByUrl(teamSeedDtos
                                        .getPicture()
                                        .getUrl());

                                team.setPicture(pictureByUrl);

                                this.teamRepository.saveAndFlush(team);
                                sb.append(String.format("%s successfully imported", team.getName()))
                                        .append(System.lineSeparator());

                            }
                        }
                    } else {
                        sb.append("Invalid team").append(System.lineSeparator());
                    }
                });

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        String path = "src/main/resources/files/xml/teams.xml";
        return String.join("", Files.readAllLines(Path.of(path)));
    }

    @Override
    public Team getTeamByName(String name) {
        return this.teamRepository.findByName(name);
    }

}
