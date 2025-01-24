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
import pl.edu.pjwstk.s32410.library.api.service.StorageBookService;
import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;

@RestController
@RequestMapping("/storage-books")
public class StorageBookController {

    @Autowired
    private StorageBookService storageBookService;

    @GetMapping
    public List<StorageBook> getAllStorageBooks() {
        return storageBookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageBook> getStorageBookById(@PathVariable UUID id) {
        Optional<StorageBook> book = storageBookService.findById(id);
        return ResponseEntity.of(book);
    }

    @PostMapping
    public StorageBook createStorageBook(@RequestBody StorageBook book) {
        book.setId(null);
    	
    	return storageBookService.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageBook> updateStorageBook(@PathVariable UUID id, @RequestBody StorageBook storageBook) {
        if (!storageBookService.existsById(id)) throw new BookNotFoundException();
        
        storageBook.setId(id);
        return ResponseEntity.ok(storageBookService.save(storageBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStorageBook(@PathVariable UUID id) {
        if (!storageBookService.existsById(id)) throw new BookNotFoundException();
        
        storageBookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reference/{referenceId}")
    public List<StorageBook> getStorageBooksByReferenceId(@PathVariable UUID referenceId) {
        return storageBookService.findByReferenceId(referenceId);
    }

    @GetMapping("/book-title/{title}")
    public List<StorageBook> getStorageBooksByBookTitle(@PathVariable String title) {
        return storageBookService.findByBookTitle(title);
    }

    @GetMapping("/book-isbn/{isbn}")
    public List<StorageBook> getStorageBooksByBookIsbn(@PathVariable String isbn) {
        return storageBookService.findByBookIsbn(isbn);
    }

    @GetMapping("/book-publisher/{publisher}")
    public List<StorageBook> getStorageBooksByBookPublisher(@PathVariable String publisher) {
        return storageBookService.findByBookPublisher(publisher);
    }

    @GetMapping("/book-language/{language}")
    public List<StorageBook> getStorageBooksByBookLanguage(@PathVariable String language) {
        return storageBookService.findByBookLanguage(language);
    }
}
