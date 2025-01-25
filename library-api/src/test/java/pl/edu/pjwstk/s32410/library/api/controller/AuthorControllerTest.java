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

import pl.edu.pjwstk.s32410.library.api.service.AuthorService;
import pl.edu.pjwstk.s32410.library.shared.model.Author;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    void getAllAuthors() throws Exception {
        List<Author> authors = Arrays.asList(new Author(), new Author());
        when(authorService.findAll()).thenReturn(authors);

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(authors.size()));
    }

    @Test
    void getAuthorById() throws Exception {
        UUID id = UUID.randomUUID();
        Author author = new Author();
        when(authorService.findById(id)).thenReturn(Optional.of(author));

        mockMvc.perform(get("/authors/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(author.getId()));
    }

    @Test
    void createAuthor() throws Exception {
        Author author = new Author();
        when(authorService.save(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(author.getId()));
    }

    @Test
    void deleteAuthor() throws Exception {
        UUID id = UUID.randomUUID();
        
        when(authorService.existsById(id)).thenReturn(true);
        doNothing().when(authorService).deleteById(id);

        mockMvc.perform(delete("/authors/{id}", id))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deleteAuthorNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        
        doNothing().when(authorService).deleteById(id);

        mockMvc.perform(delete("/authors/{id}", id))
                .andExpect(status().isNotFound());
    }
}
