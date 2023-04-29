package emt.labs.library.service.impl;

import emt.labs.library.model.Author;
import emt.labs.library.model.Book;
import emt.labs.library.model.dto.BookDto;
import emt.labs.library.model.enumerations.Category;
import emt.labs.library.model.exceptions.AuthorNotFoundException;
import emt.labs.library.model.exceptions.BookNotFoundException;
import emt.labs.library.repository.AuthorRepository;
import emt.labs.library.repository.BookRepository;
import emt.labs.library.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> findAllWithPagination(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> save(String name, Long authorId, Integer availableCopies, Category category) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        Book book = new Book(name, author, availableCopies, category);
        bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> save(BookDto bookDto) {
        Author author = this.authorRepository.findById(bookDto.getAuthor()).orElseThrow();
        Category category = bookDto.getCategory();

        Book book = new Book(bookDto.getName(), author, bookDto.getAvailableCopies(), category);
        this.bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Long id, String name, Long authorId, Integer availableCopies, Category category) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
        book.setAuthor(author);
        book.setCategory(category);
        book.setName(name);
        if(book.getAvailableCopies() != 0) {
            book.setAvailableCopies(availableCopies);
        }
        bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Long id, BookDto bookDto) {
            Book book = this.bookRepository.findById(id).orElseThrow();
            Author author = this.authorRepository.findById(bookDto.getAuthor()).orElseThrow();
            Category category = bookDto.getCategory();

            book.setName(bookDto.getName());
            book.setAuthor(author);
            if(bookDto.getAvailableCopies() != 0) {
                book.setAvailableCopies(bookDto.getAvailableCopies());
            }
            book.setCategory(category);

            return Optional.of(this.bookRepository.save(book));
    }
}
