package emt.labs.library.web.rest;

import emt.labs.library.model.Book;
import emt.labs.library.model.dto.BookDto;
import emt.labs.library.model.enumerations.Category;
import emt.labs.library.model.exceptions.BookNotFoundException;
import emt.labs.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    private List<Book> findAll() {
        return this.bookService.findAll();
    }

    @GetMapping("/pagination")
    private Page<Book> findAllWithPagination(Pageable pageable) {
        return this.bookService.findAllWithPagination(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        return this.bookService.findById(id)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PostMapping("/add")
//    public ResponseEntity<Book> save(@RequestParam String name,
//                                     @RequestParam Long authorId,
//                                     @RequestParam Integer availableCopies,
//                                     @RequestParam Category category) {
//        return this.bookService.save(name, authorId, availableCopies, category)
//                .map(product -> ResponseEntity.ok().body(product))
//                .orElseGet(() -> ResponseEntity.badRequest().build());
//    }
//
//    @PutMapping("/edit/{id}")
//    public ResponseEntity<Book> save(@PathVariable Long id,
//                                     @RequestParam String name,
//                                     @RequestParam Long authorId,
//                                     @RequestParam Integer availableCopies,
//                                     @RequestParam Category category) {
//        return this.bookService.edit(id, name, authorId, availableCopies, category)
//                .map(product -> ResponseEntity.ok().body(product))
//                .orElseGet(() -> ResponseEntity.badRequest().build());
//    }

    @PostMapping("/add")
    public ResponseEntity<Book> save(@RequestBody BookDto bookDto) {
        return this.bookService.save(bookDto)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> edit(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return this.bookService.edit(id, bookDto)
                .map(book1 -> ResponseEntity.ok().body(book1))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/mark-as-taken/{id}")
    public List<Book> takeBook(@PathVariable Long id) {
        Book book = this.bookService.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        if(book.getAvailableCopies() > 0) book.setAvailableCopies(book.getAvailableCopies() - 1);
        this.bookService.edit(book.getId(), book.getName(), book.getAuthor().getId(), book.getAvailableCopies(), book.getCategory());
        return this.bookService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        this.bookService.deleteById(id);
        if(this.bookService.findById(id).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

}
