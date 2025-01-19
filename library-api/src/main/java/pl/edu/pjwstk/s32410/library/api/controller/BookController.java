package pl.edu.pjwstk.s32410.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.s32410.library.api.service.BookService;
import pl.edu.pjwstk.s32410.library.shared.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody Book book) {
        if (!bookService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        book.setId(id);
        return ResponseEntity.ok(bookService.save(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        if (!bookService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/title")
    public List<Book> getBooksByTitle(@RequestParam String title) {
        return bookService.findByTitle(title);
    }

    @GetMapping("/publication-year")
    public List<Book> getBooksByPublicationYear(@RequestParam int year) {
        return bookService.findByPublicationYear(year);
    }

    @GetMapping("/isbn")
    public List<Book> getBooksByIsbn(@RequestParam String isbn) {
        return bookService.findByIsbn(isbn);
    }

    @GetMapping("/language")
    public List<Book> getBooksByLanguage(@RequestParam String language) {
        return bookService.findByLanguage(language);
    }

    @GetMapping("/publisher")
    public List<Book> getBooksByPublisher(@RequestParam String publisher) {
        return bookService.findByPublisher(publisher);
    }
}
