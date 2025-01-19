package pl.edu.pjwstk.s32410.library.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s32410.library.api.repository.StorageBookRepository;
import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StorageBookService {

    @Autowired
    private StorageBookRepository storageBookRepository;

    public List<StorageBook> findAll() {
        return storageBookRepository.findAll();
    }

    public Optional<StorageBook> findById(UUID id) {
        return storageBookRepository.findById(id);
    }

    public StorageBook save(StorageBook storageBook) {
        return storageBookRepository.save(storageBook);
    }

    public void deleteById(UUID id) {
        storageBookRepository.deleteById(id);
    }

    public List<StorageBook> findBySiteId(UUID siteId) {
        return storageBookRepository.findBySiteId(siteId);
    }

    public List<StorageBook> findByReferenceId(UUID referenceId) {
        return storageBookRepository.findByReferenceId(referenceId);
    }

    public List<StorageBook> findBySiteName(String siteName) {
        return storageBookRepository.findBySiteName(siteName);
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
