package exam.library.services.impl;

import exam.library.models.entities.Book;
import exam.library.models.entities.Library;
import exam.library.models.entities.dtos.xml.libraryDto.LibraryRootSeedDtos;
import exam.library.repositories.BookRepository;
import exam.library.repositories.LibraryRepository;
import exam.library.services.LibraryService;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class LibraryServiceImpl implements LibraryService {
    private final String LIBRARIES_PATH = "src/main/resources/files/xml/libraries.xml";
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LibraryServiceImpl(XmlParser xmlParser, ValidatorUtil validatorUtil, ModelMapper modelMapper, LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean areImported() {
        return this.libraryRepository.count() > 0;
    }

    @Override
    public String readLibrariesFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(LIBRARIES_PATH)));
    }

    @Override
    public String importLibraries() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        LibraryRootSeedDtos libraryRootSeedDtos = this.
                xmlParser.parseXml(LibraryRootSeedDtos.class, LIBRARIES_PATH);

        libraryRootSeedDtos.getLibraries().forEach(libradyDto -> {
            if (this.validatorUtil.isValid(libradyDto) && this.libraryRepository.findByName(
                    libradyDto.getName()) == null) {

                Library library = this.modelMapper.map(libradyDto, Library.class);
                Book book = this.bookRepository.findById(libradyDto.getBook().getId());
                if (book != null) {
                    Set<Book> books = new HashSet<>();
                    books.add(book);
                    library.setBooks(books);
                    this.libraryRepository.saveAndFlush(library);
                    sb.append(String.format("Successfully added Library: %s %s%n",
                            library.getName(), library.getLocation()));

                }
            } else {
                sb.append("Invalid Library").append(System.lineSeparator());
            }

        });
        return sb.toString();
    }
}
