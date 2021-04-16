package exam.library.services;

import exam.library.models.entities.Author;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface AuthorService {
    boolean areImported();
    String readAuthorsFileContent() throws IOException;
    String importAuthors() throws FileNotFoundException;

    Author findByName(String first, String last);
}
