
package pl.edu.pjwstk.s32410.library.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s32410.library.api.repository.StorageBookRepository;
import pl.edu.pjwstk.s32410.library.shared.model.book.StorageBook;

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
}
