package pl.edu.pjwstk.s32410.library.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.edu.pjwstk.s32410.library.api.repository.ImageRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Image;

public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExists() {
        Image image = new Image();
        image.setId(UUID.randomUUID());
        when(imageRepository.existsById(image.getId())).thenReturn(true);
        assertTrue(imageService.exists(image));
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Image image = new Image();
        when(imageRepository.findById(id)).thenReturn(Optional.of(image));
        assertEquals(Optional.of(image), imageService.findById(id));
    }

    @Test
    void testSave() {
        Image image = new Image();
        when(imageRepository.save(image)).thenReturn(image);
        assertEquals(image, imageService.save(image));
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(imageRepository).deleteById(id);
        imageService.deleteById(id);
        verify(imageRepository, times(1)).deleteById(id);
    }
}
