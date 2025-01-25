package pl.edu.pjwstk.s32410.projects.library.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.projects.library.app.config.AppConfig;

class LibraryServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AppConfig apiConfig;

    @InjectMocks
    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchBooks() {
        String json = "[{\"title\":\"Book1\",\"description\":\"Desc1\",\"authors\":[]}]";
        when(apiConfig.getApiBaseUrl()).thenReturn("http://api.example.com/");
        when(restTemplate.getForObject("http://api.example.com/books", String.class)).thenReturn(json);

        List<Book> books = libraryService.searchBooks("Book1");

        assertEquals(1, books.size());
        assertEquals("Book1", books.get(0).getTitle());
    }

    @Test
    void testGetBooksStorageCount() {
        String json = "[{\"reference\":{\"id\":\"123e4567-e89b-12d3-a456-426614174000\"},\"count\":5}]";
        when(apiConfig.getApiBaseUrl()).thenReturn("http://api.example.com/");
        when(restTemplate.getForObject("http://api.example.com/storage-books", String.class)).thenReturn(json);

        Set<UUID> bookIds = new HashSet<>();
        bookIds.add(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));

        Map<UUID, Integer> storageCount = libraryService.getBooksStorageCount(bookIds);

        assertEquals(1, storageCount.size());
        assertEquals(1, storageCount.get(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")));
    }
}
