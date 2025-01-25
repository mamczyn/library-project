package pl.edu.pjwstk.s32410.library.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import pl.edu.pjwstk.s32410.library.api.repository.BookRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Author;
import pl.edu.pjwstk.s32410.library.shared.model.Book;
import pl.edu.pjwstk.s32410.library.shared.model.Category;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExists() {
        Book book = new Book();
        book.setId(UUID.randomUUID());
        when(bookRepository.existsById(book.getId())).thenReturn(true);
        assertTrue(bookService.exists(book));
    }

    @Test
    void testFindAll() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);
        assertEquals(books, bookService.findAll());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Book book = new Book();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        assertEquals(Optional.of(book), bookService.findById(id));
    }

    @Test
    void testSave() {
        Book book = new Book();
        book.setAuthors(Arrays.asList(new Author()));
        book.setCategories(Arrays.asList(new Category()));
        when(authorService.exists(any(Author.class))).thenReturn(true);
        when(categoryService.exists(any(Category.class))).thenReturn(true);
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book, bookService.save(book));
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(bookRepository).deleteById(id);
        bookService.deleteById(id);
        verify(bookRepository, times(1)).deleteById(id);
    }
}
