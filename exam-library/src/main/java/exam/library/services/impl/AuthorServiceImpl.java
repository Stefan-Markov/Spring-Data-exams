package exam.library.services.impl;

import com.google.gson.Gson;
import exam.library.models.entities.Author;
import exam.library.models.entities.dtos.gson.AuthorSeedGsonDtos;
import exam.library.repositories.AuthorRepository;
import exam.library.services.AuthorService;
import exam.library.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final String AUTHOR_PATH = "src/main/resources/files/json/authors.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(ModelMapper modelMapper, Gson gson, ValidatorUtil validatorUtil, AuthorRepository authorRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.authorRepository = authorRepository;
    }

    @Override
    public boolean areImported() {
        return this.authorRepository.count() > 0;
    }

    @Override
    public String readAuthorsFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(AUTHOR_PATH)));
    }

    @Override
    public String importAuthors() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        AuthorSeedGsonDtos[] authorSeedGsonDtos = this.gson.fromJson(new FileReader(AUTHOR_PATH), AuthorSeedGsonDtos[].class);

        Arrays.stream(authorSeedGsonDtos).forEach(authorDto -> {
            if (this.validatorUtil.isValid(authorDto) && this.authorRepository.findAllByFirstNameAndLastName(
                    authorDto.getFirstName(), authorDto.getLastName()) == null) {

                Author author = this.modelMapper.map(authorDto, Author.class);

                this.authorRepository.saveAndFlush(author);
                sb.append(String.format("Successfully imported Author: %s %s - %s%n", author.getFirstName(),
                        author.getLastName(),author.getBirthTown()));
            } else {
                sb.append("Invalid Author").append(System.lineSeparator());

            }
        });
        return sb.toString();
    }

    @Override
    public Author findByName(String first, String last) {
        return this.authorRepository.findAllByFirstNameAndLastName(first, last);
    }
}
