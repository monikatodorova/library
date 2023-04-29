package emt.labs.library.web.controller;

import emt.labs.library.model.Author;
import emt.labs.library.model.Book;
import emt.labs.library.model.enumerations.Category;
import emt.labs.library.service.AuthorService;
import emt.labs.library.service.BookService;
import emt.labs.library.service.CountryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping({"/", "/books"})
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getBooksPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("bodyContent", "books");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        this.bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.bookService.findById(id).isPresent()) {
            Book book = this.bookService.findById(id).get();
            List<Author> authors = this.authorService.findAll();
            List<Category> categories = Arrays.stream(Category.values()).toList();
            model.addAttribute("authors", authors);
            model.addAttribute("categories", categories);
            model.addAttribute("book", book);
            model.addAttribute("bodyContent", "add-book");
            return "master-template";
        }
        return "redirect:/books?error=BookNotFound";
    }

    @GetMapping("/mark-as-taken/{id}")
    public String takeBook(@PathVariable Long id) {
        if (this.bookService.findById(id).isPresent()) {
            Book book = this.bookService.findById(id).get();
            bookService.edit(id, book.getName(), book.getAuthor().getId(), book.getAvailableCopies()-1, book.getCategory());
            return "redirect:/books";
        }
        return "redirect:/books?error=BookNotFound";
    }

    @GetMapping("/add-form")
    public String addProductPage(Model model) {
        List<Author> authors = this.authorService.findAll();
        List<Category> categories = Arrays.stream(Category.values()).toList();
        model.addAttribute("authors", authors);
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "add-book");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProduct(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam Long authorId,
            @RequestParam Integer availableCopies,
            @RequestParam Category category) {
        if (id != null) {
            this.bookService.edit(id, name, authorId, availableCopies, category);
        } else {
            this.bookService.save(name, authorId, availableCopies, category);
        }
        return "redirect:/books";
    }
}
