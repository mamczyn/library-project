
package pl.edu.pjwstk.s32410.library.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s32410.library.api.repository.BookRepository;
import pl.edu.pjwstk.s32410.library.shared.model.book.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(UUID id) {
        bookRepository.deleteById(id);
    }
}
