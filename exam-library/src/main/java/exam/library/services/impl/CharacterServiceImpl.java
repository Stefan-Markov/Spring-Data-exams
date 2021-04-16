package exam.library.services.impl;

import exam.library.models.entities.Book;
import exam.library.models.entities.Character;
import exam.library.models.entities.dtos.xml.CharacterRootXmlDtos;
import exam.library.repositories.BookRepository;
import exam.library.repositories.CharacterRepository;
import exam.library.services.CharacterService;
import exam.library.util.ValidatorUtil;
import exam.library.util.XmlParser;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
public class CharacterServiceImpl implements CharacterService {
    private final String CHARACTERS_PATH = "src/main/resources/files/xml/characters.xml";
    private final CharacterRepository characterRepository;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository, XmlParser xmlParser, ValidatorUtil validatorUtil, ModelMapper modelMapper, BookRepository bookRepository) {
        this.characterRepository = characterRepository;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean areImported() {
        return this.characterRepository.count() > 0;
    }

    @Override
    public String readCharactersFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(CHARACTERS_PATH)));
    }

    @Override
    public String importCharacters() throws JAXBException {
        StringBuilder sb = new StringBuilder();
        CharacterRootXmlDtos characterRootXmlDtos = this.xmlParser.parseXml(CharacterRootXmlDtos.class,
                CHARACTERS_PATH);

        characterRootXmlDtos.getCharacter().forEach(characterDto -> {
            if (this.validatorUtil.isValid(characterDto) && this.characterRepository.findByFirstNameAndLastName(
                    characterDto.getFirstName(), characterDto.getLastName()) == null) {

                Character character = this.modelMapper.map(characterDto, Character.class);
                Book book = this.bookRepository.findById(characterDto.getBookDto().getId());

                if (book != null) {
                    character.setBook(book);
                    this.characterRepository.saveAndFlush(character);
                    sb.append(String.format("Successful imported character: %s %s%n",
                            character.getFirstName(), character.getLastName()));
                } else {
                    sb.append("Invalid Character").append(System.lineSeparator());
                }

            } else {
                sb.append("Invalid Character").append(System.lineSeparator());
            }

        });

        return sb.toString();
    }

    @Override
    public String findCharactersInBookOrderedByLastNameDescendingThenByAge() {
        StringBuilder sb = new StringBuilder();

        List<Character> allByAge = this.characterRepository.findAllByAge();

        allByAge.forEach(character -> {
            sb.append(String.format("Character name %s %s %s, age %d, in book %s",
                    character.getFirstName(),character.getMiddleName(), character.getLastName(),
                    character.getAge(),character.getBook().getName())).append(System.lineSeparator());
        });

        return sb.toString();
    }
}
