package exam.library.web.controllers;

import exam.library.services.AuthorService;
import exam.library.services.BookService;
import exam.library.services.CharacterService;
import exam.library.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class HomeController extends BaseController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final LibraryService libraryService;
    private final CharacterService characterService;

    @Autowired
    public HomeController(AuthorService authorService, BookService bookService, LibraryService libraryService, CharacterService characterService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.libraryService = libraryService;
        this.characterService = characterService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        boolean areImported = this.authorService.areImported() &&
                this.bookService.areImported() &&
                this.libraryService.areImported() &&
                this.characterService.areImported();

        return super.view("index", "areImported", areImported);
    }
}
