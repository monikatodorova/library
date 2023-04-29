package emt.labs.library.service;

import emt.labs.library.model.Author;
import emt.labs.library.model.Book;
import emt.labs.library.model.dto.BookDto;
import emt.labs.library.model.enumerations.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Page<Book> findAllWithPagination(Pageable pageable);
    Optional<Book> findById(Long id);
    void deleteById(Long id);
    Optional<Book> save(String name, Long author, Integer availableCopies, Category category);
    Optional<Book> save(BookDto bookDto);
    Optional<Book> edit(Long id, String name, Long author, Integer availableCopies, Category category);
    Optional<Book> edit(Long id, BookDto bookDto);
}
