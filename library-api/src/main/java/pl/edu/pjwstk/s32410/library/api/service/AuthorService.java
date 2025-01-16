
package pl.edu.pjwstk.s32410.library.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s32410.library.api.repository.AuthorRepository;
import pl.edu.pjwstk.s32410.library.shared.model.book.Author;

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

    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public void deleteById(UUID id) {
        authorRepository.deleteById(id);
    }
}
