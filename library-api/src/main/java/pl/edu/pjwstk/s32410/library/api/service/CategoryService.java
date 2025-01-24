package pl.edu.pjwstk.s32410.library.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pl.edu.pjwstk.s32410.library.api.repository.CategoryRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Category;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public boolean exists(Category category) {
    	return category != null &&  existsById(category.getId());
    }
    
    public boolean existsById(UUID id) {
    	return categoryRepository.existsById(id);
    }
    
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Cacheable(value = "category", key = "#id", unless = "#result == null")
    public Optional<Category> findById(UUID id) {
        return categoryRepository.findById(id);
    }

    @CacheEvict(value = "category", key = "#category.id")
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @CacheEvict(value = "category", key = "#id")
    public void deleteById(UUID id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> findByNameContaining(String name) {
        return categoryRepository.findByNameContaining(name);
    }
}
