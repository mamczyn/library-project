package pl.edu.pjwstk.s32410.library.api.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.pjwstk.s32410.library.api.repository.ImageRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Image;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Optional<Image> findById(UUID id) {
        return imageRepository.findById(id);
    }

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public void deleteById(UUID id) {
        imageRepository.deleteById(id);
    }
}

