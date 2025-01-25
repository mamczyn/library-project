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

import pl.edu.pjwstk.s32410.library.api.repository.AuthorRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Author;

public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExists() {
        Author author = new Author();
        author.setId(UUID.randomUUID());
        when(authorRepository.existsById(author.getId())).thenReturn(true);
        assertTrue(authorService.exists(author));
    }

    @Test
    void testFindAll() {
        List<Author> authors = Arrays.asList(new Author(), new Author());
        when(authorRepository.findAll()).thenReturn(authors);
        assertEquals(authors, authorService.findAll());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Author author = new Author();
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        assertEquals(Optional.of(author), authorService.findById(id));
    }

    @Test
    void testSave() {
        Author author = new Author();
        when(authorRepository.save(author)).thenReturn(author);
        assertEquals(author, authorService.save(author));
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(authorRepository).deleteById(id);
        authorService.deleteById(id);
        verify(authorRepository, times(1)).deleteById(id);
    }
}