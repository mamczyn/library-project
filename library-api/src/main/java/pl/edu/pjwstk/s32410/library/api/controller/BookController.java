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

import pl.edu.pjwstk.s32410.library.api.exceptions.book.BookNotFoundException;
import pl.edu.pjwstk.s32410.library.api.service.BookService;
import pl.edu.pjwstk.s32410.library.shared.model.Book;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        Optional<Book> book = bookService.findById(id);
        return ResponseEntity.of(book);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
    	book.setId(null);
    	
    	return bookService.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody Book book) {
        if (!bookService.existsById(id)) throw new BookNotFoundException();
        
        book.setId(id);
        return ResponseEntity.ok(bookService.save(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        if (!bookService.existsById(id)) throw new BookNotFoundException();
        
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return bookService.findByTitle(title);
    }

    @GetMapping("/publication-year/{year}")
    public List<Book> getBooksByPublicationYear(@PathVariable int year) {
        return bookService.findByPublicationYear(year);
    }

    @GetMapping("/isbn/{isbn}")
    public List<Book> getBooksByIsbn(@PathVariable String isbn) {
        return bookService.findByIsbn(isbn);
    }

    @GetMapping("/language/{language}")
    public List<Book> getBooksByLanguage(@PathVariable String language) {
        return bookService.findByLanguage(language);
    }

    @GetMapping("/publisher/{publisher}")
    public List<Book> getBooksByPublisher(@PathVariable String publisher) {
        return bookService.findByPublisher(publisher);
    }
}
