package pl.edu.pjwstk.s32410.library.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pjwstk.s32410.library.api.exceptions.author.AuthorNotFoundException;
import pl.edu.pjwstk.s32410.library.api.service.AuthorService;
import pl.edu.pjwstk.s32410.library.shared.model.Author;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable UUID id) {
        Optional<Author> author = authorService.findById(id);
        
        return ResponseEntity.of(author);
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
    	author.setId(null);
    	
    	return authorService.save(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable UUID id, @RequestBody Author author) {
        if (!authorService.existsById(id)) throw new AuthorNotFoundException();
        
        author.setId(id);
        return ResponseEntity.ok(authorService.save(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
        if (!authorService.existsById(id)) throw new AuthorNotFoundException();
        
        authorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public List<Author> getAuthorsByName(@PathVariable String name) {
        return authorService.findByName(name);
    }

    @GetMapping("/surname/{surname}")
    public List<Author> getAuthorsBySurname(@PathVariable String surname) {
        return authorService.findBySurname(surname);
    }

    @GetMapping("/nationality/{nationality}")
    public List<Author> getAuthorsByNationality(@PathVariable String nationality) {
        return authorService.findByNationality(nationality);
    }

    @GetMapping("/biography/{biography}")
    public List<Author> getAuthorsByBiographyKeyword(@PathVariable String biography) {
        return authorService.findByBiographyKeyword(biography);
    }
}
