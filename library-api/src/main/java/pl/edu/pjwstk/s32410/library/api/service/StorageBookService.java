package pl.edu.pjwstk.s32410.library.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pl.edu.pjwstk.s32410.library.api.repository.StorageBookRepository;
import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;

@Service
public class StorageBookService {

    @Autowired
    private StorageBookRepository storageBookRepository;
    
    public boolean exists(StorageBook book) {
    	return book != null &&  existsById(book.getId());
    }
    
    public boolean existsById(UUID id) {
    	return storageBookRepository.existsById(id);
    }

    public List<StorageBook> findAll() {
        return storageBookRepository.findAll();
    }

    @Cacheable(value = "storage", key = "#id", unless = "#result == null")
    public Optional<StorageBook> findById(UUID id) {
        return storageBookRepository.findById(id);
    }

    @CacheEvict(value = "storage", key = "#book.id")
    public StorageBook save(StorageBook book) {
        return storageBookRepository.save(book);
    }

    @CacheEvict(value = "storage", key = "#id")
    public void deleteById(UUID id) {
        storageBookRepository.deleteById(id);
    }

    public List<StorageBook> findByReferenceId(UUID referenceId) {
        return storageBookRepository.findByReferenceId(referenceId);
    }

    public List<StorageBook> findByBookTitle(String title) {
        return storageBookRepository.findByBookTitle(title);
    }

    public List<StorageBook> findByBookIsbn(String isbn) {
        return storageBookRepository.findByBookIsbn(isbn);
    }

    public List<StorageBook> findByBookPublisher(String publisher) {
        return storageBookRepository.findByBookPublisher(publisher);
    }

    public List<StorageBook> findByBookLanguage(String language) {
        return storageBookRepository.findByBookLanguage(language);
    }
}
