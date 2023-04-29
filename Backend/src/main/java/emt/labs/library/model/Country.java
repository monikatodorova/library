package emt.labs.library.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String continent;
    private String name;

    public Country(String continent, String name) {
        this.continent = continent;
        this.name = name;
    }

    public Country() {

    }
}
