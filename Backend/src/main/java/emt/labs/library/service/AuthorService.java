package emt.labs.library.service;

import emt.labs.library.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    void deleteById(Long id);
    Optional<Author> save(String name, String surname, Long country);
    Optional<Author> edit(Long id, String name, String surname, Long country);
}
