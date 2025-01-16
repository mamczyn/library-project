
package pl.edu.pjwstk.s32410.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.s32410.library.api.service.StorageBookService;
import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        Optional<StorageBook> storageBook = storageBookService.findById(id);
        return storageBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public StorageBook createStorageBook(@RequestBody StorageBook storageBook) {
        return storageBookService.save(storageBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageBook> updateStorageBook(@PathVariable UUID id, @RequestBody StorageBook storageBook) {
        if (!storageBookService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        storageBook.setId(id);
        return ResponseEntity.ok(storageBookService.save(storageBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStorageBook(@PathVariable UUID id) {
        if (!storageBookService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        storageBookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
