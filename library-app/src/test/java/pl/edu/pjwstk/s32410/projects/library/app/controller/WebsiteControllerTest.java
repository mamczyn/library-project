package pl.edu.pjwstk.s32410.projects.library.app.controller;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.projects.library.app.service.LibraryService;

class WebsiteControllerTest {
    @Mock
    private LibraryService libraryService;

    @Mock
    private Model model;

    @InjectMocks
    private WebsiteController websiteController;
    
    private MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
    	/* Annotacja @WebMvcTest nie dziala, obejscie przez stworzenie custom setup */
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(websiteController).build();
    }

    @Test
    void testGetSearchPage() throws Exception {
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setTitle("Book1");
        books.add(book);

        Map<UUID, Integer> storageCount = new HashMap<>();
        storageCount.put(UUID.randomUUID(), 1);

        when(libraryService.searchBooks(anyString())).thenReturn(books);
        when(libraryService.getBooksStorageCount(anySet())).thenReturn(storageCount);

        mockMvc.perform(get("/").param("search", "Book1"))
                .andExpect(status().isOk())
                .andExpect(view().name("search"))
                .andExpect(model().attributeExists("search"))
                .andExpect(model().attributeExists("results"))
                .andExpect(model().attributeExists("storage"));
    }
}
