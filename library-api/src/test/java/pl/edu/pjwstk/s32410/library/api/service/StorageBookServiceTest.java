package pl.edu.pjwstk.s32410.library.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.edu.pjwstk.s32410.library.api.repository.StorageBookRepository;
import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;

public class StorageBookServiceTest {

    @Mock
    private StorageBookRepository storageBookRepository;

    @InjectMocks
    private StorageBookService storageBookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExists() {
        StorageBook book = new StorageBook();
        book.setId(UUID.randomUUID());
        when(storageBookRepository.existsById(book.getId())).thenReturn(true);
        assertTrue(storageBookService.exists(book));
    }

    @Test
    void testFindAll() {
        List<StorageBook> books = Arrays.asList(new StorageBook(), new StorageBook());
        when(storageBookRepository.findAll()).thenReturn(books);
        assertEquals(books, storageBookService.findAll());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        StorageBook book = new StorageBook();
        when(storageBookRepository.findById(id)).thenReturn(Optional.of(book));
        assertEquals(Optional.of(book), storageBookService.findById(id));
    }

    @Test
    void testSave() {
        StorageBook book = new StorageBook();
        when(storageBookRepository.save(book)).thenReturn(book);
        assertEquals(book, storageBookService.save(book));
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(storageBookRepository).deleteById(id);
        storageBookService.deleteById(id);
        verify(storageBookRepository, times(1)).deleteById(id);
    }
}
