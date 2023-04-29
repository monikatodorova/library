package emt.labs.library.model.dto;

import emt.labs.library.model.enumerations.Category;
import lombok.Data;

@Data
public class BookDto {
    private String name;

    private Long author;

    private Integer  availableCopies;

    private Category category;

    public BookDto() {
    }

    public BookDto(String name, Category category, Long author, Integer availableCopies) {
        this.name = name;
        this.category = category;
        this.author = author;
        this.availableCopies = availableCopies;
    }
}
