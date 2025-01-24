package pl.edu.pjwstk.s32410.library.api.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pl.edu.pjwstk.s32410.library.api.repository.ImageRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Image;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public boolean exists(Image image) {
    	return image != null &&  existsById(image.getId());
    }
    
    public boolean existsById(UUID id) {
    	return imageRepository.existsById(id);
    }
    
    @Cacheable(value = "image", key = "#id", unless = "#result == null")
    public Optional<Image> findById(UUID id) {
        return imageRepository.findById(id);
    }

    @CacheEvict(value = "image", key = "#image.id")
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @CacheEvict(value = "image", key = "#id")
    public void deleteById(UUID id) {
        imageRepository.deleteById(id);
    }
}

