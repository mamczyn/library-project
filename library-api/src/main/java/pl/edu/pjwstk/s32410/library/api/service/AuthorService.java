package pl.edu.pjwstk.s32410.library.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s32410.library.api.repository.AuthorRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }
    
    public boolean exists(Author author) {
    	return author != null &&  existsById(author.getId());
    }
    
    public boolean existsById(UUID id) {
    	return authorRepository.existsById(id);
    }

    @Cacheable(value = "author", key = "#id", unless = "#result == null")
    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    @CacheEvict(value = "author", key = "#author.id")
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @CacheEvict(value = "author", key = "#id")
    public void deleteById(UUID id) {
        authorRepository.deleteById(id);
    }

    public List<Author> findByName(String name) {
        return authorRepository.findByName(name);
    }

    public List<Author> findBySurname(String surname) {
        return authorRepository.findBySurname(surname);
    }

    public List<Author> findByNationality(String nationality) {
        return authorRepository.findByNationality(nationality);
    }

    public List<Author> findByBiographyKeyword(String keyword) {
        return authorRepository.findByBiographyKeyword(keyword);
    }
}
