package pl.edu.pjwstk.s32410.library.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pl.edu.pjwstk.s32410.library.api.service.StorageBookService;
import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;



@WebMvcTest(StorageBookController.class)
class StorageBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorageBookService storageBookService;

    @Test
    void getAllStorageBooks() throws Exception {
        List<StorageBook> books = Arrays.asList(new StorageBook(), new StorageBook());
        when(storageBookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/storage-books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(books.size()));
    }

    @Test
    void getStorageBookById() throws Exception {
        UUID id = UUID.randomUUID();
        StorageBook book = new StorageBook();
        when(storageBookService.findById(id)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/storage-books/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()));
    }

    @Test
    void createStorageBook() throws Exception {
        StorageBook book = new StorageBook();
        when(storageBookService.save(any(StorageBook.class))).thenReturn(book);

        mockMvc.perform(post("/storage-books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()));
    }

    @Test
    void deleteStorageBook() throws Exception {
        UUID id = UUID.randomUUID();
        
        when(storageBookService.existsById(id)).thenReturn(true);
        doNothing().when(storageBookService).deleteById(id);

        mockMvc.perform(delete("/storage-books/{id}", id))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deleteStorageBookNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        
        doNothing().when(storageBookService).deleteById(id);

        mockMvc.perform(delete("/storage-books/{id}", id))
                .andExpect(status().isNotFound());
    }
}
