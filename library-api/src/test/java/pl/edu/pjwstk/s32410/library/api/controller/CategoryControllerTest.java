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

import pl.edu.pjwstk.s32410.library.api.service.CategoryService;
import pl.edu.pjwstk.s32410.library.shared.model.Category;



@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void getAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(categories.size()));
    }

    @Test
    void getCategoryById() throws Exception {
        UUID id = UUID.randomUUID();
        Category category = new Category();
        when(categoryService.findById(id)).thenReturn(Optional.of(category));

        mockMvc.perform(get("/categories/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId()));
    }

    @Test
    void createCategory() throws Exception {
        Category category = new Category();
        when(categoryService.save(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId()));
    }

    @Test
    void deleteCategory() throws Exception {
        UUID id = UUID.randomUUID();
        
        when(categoryService.existsById(id)).thenReturn(true);
        doNothing().when(categoryService).deleteById(id);

        mockMvc.perform(delete("/categories/{id}", id))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deleteCategoryNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        
        doNothing().when(categoryService).deleteById(id);

        mockMvc.perform(delete("/categories/{id}", id))
                .andExpect(status().isNotFound());
    }
}