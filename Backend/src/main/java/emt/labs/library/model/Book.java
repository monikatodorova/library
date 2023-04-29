package emt.labs.library.model;

import emt.labs.library.model.enumerations.Category;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Author author;
    private Integer availableCopies;
    private Category category;

    public Book(String name, Author author, Integer availableCopies, Category category) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.availableCopies = availableCopies;
        this.category = category;
    }

    public Book() {

    }

    public String getCategoryName() {
        return category.name();
    }
}
