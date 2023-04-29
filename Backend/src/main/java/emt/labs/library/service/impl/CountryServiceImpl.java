package emt.labs.library.service.impl;

import emt.labs.library.model.Country;
import emt.labs.library.model.exceptions.CountryNotFoundException;
import emt.labs.library.repository.CountryRepository;
import emt.labs.library.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.countryRepository.deleteById(id);
    }

    @Override
    public Optional<Country> save(String name, String continent) {
        Country country = new Country(name, continent);
        countryRepository.save(country);
        return Optional.of(country);
    }

    @Override
    public Optional<Country> edit(Long id, String name, String continent) {
        Country country = countryRepository.findById(id).orElseThrow(() -> new CountryNotFoundException(id));
        country.setContinent(continent);
        country.setName(name);
        countryRepository.save(country);
        return Optional.of(country);
    }
}
