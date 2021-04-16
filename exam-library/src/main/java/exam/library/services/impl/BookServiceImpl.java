package exam.library.services.impl;

import com.google.gson.Gson;
import exam.library.models.entities.Author;
import exam.library.models.entities.Book;
import exam.library.models.entities.dtos.gson.BookSeedGsonDto;
import exam.library.repositories.AuthorRepository;
import exam.library.repositories.BookRepository;
import exam.library.services.BookService;
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
public class BookServiceImpl implements BookService {
    private final String BOOKS_PATH = "src/main/resources/files/json/books.json";
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(Gson gson, ValidatorUtil validatorUtil, BookRepository bookRepository, ModelMapper modelMapper, AuthorRepository authorRepository) {
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
    }

    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(BOOKS_PATH)));
    }

    @Override
    public String importBooks() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        BookSeedGsonDto[] bookSeedGsonDtos = this.gson
                .fromJson(new FileReader(BOOKS_PATH), BookSeedGsonDto[].class);

        Arrays.stream(bookSeedGsonDtos).forEach(bookDto -> {
            if (this.validatorUtil.isValid(bookDto) &&
                    this.bookRepository.findBookByName(bookDto.getName()) == null) {

                Book book = this.modelMapper.map(bookDto, Book.class);
                Author author = this.authorRepository.findById(bookDto.getAuthor());

                book.setAuthor(author);
                this.bookRepository.saveAndFlush(book);
                sb.append(String.format("Successfully imported Book: %s %s%n",
                        book.getName(), book.getWritten()));

            } else {
                sb.append("Invalid Book").append(System.lineSeparator());
            }
        });

        return sb.toString();
    }
}
