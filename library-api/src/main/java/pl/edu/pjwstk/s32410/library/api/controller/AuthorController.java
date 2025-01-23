package pl.edu.pjwstk.s32410.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.s32410.library.api.service.AuthorService;
import pl.edu.pjwstk.s32410.library.shared.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.save(author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable UUID id, @RequestBody Author author) {
        if (!authorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        author.setId(id);
        return ResponseEntity.ok(authorService.save(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
        if (!authorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
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
