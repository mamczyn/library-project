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

import pl.edu.pjwstk.s32410.library.api.repository.CategoryRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Category;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExists() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        when(categoryRepository.existsById(category.getId())).thenReturn(true);
        assertTrue(categoryService.exists(category));
    }

    @Test
    void testFindAll() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);
        assertEquals(categories, categoryService.findAll());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Category category = new Category();
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        assertEquals(Optional.of(category), categoryService.findById(id));
    }

    @Test
    void testSave() {
        Category category = new Category();
        when(categoryRepository.save(category)).thenReturn(category);
        assertEquals(category, categoryService.save(category));
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(categoryRepository).deleteById(id);
        categoryService.deleteById(id);
        verify(categoryRepository, times(1)).deleteById(id);
    }
}
