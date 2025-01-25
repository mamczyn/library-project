package pl.edu.pjwstk.s32410.library.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pl.edu.pjwstk.s32410.library.api.service.ImageService;
import pl.edu.pjwstk.s32410.library.shared.model.Image;



@WebMvcTest(ImageController.class)
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Test
    void getImageById() throws Exception {
        UUID id = UUID.randomUUID();
        Image image = new Image();
        image.setBase64Data(Base64.getEncoder().encodeToString("imageData".getBytes()));
        when(imageService.findById(id)).thenReturn(Optional.of(image));

        mockMvc.perform(get("/images/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "image/jpeg"));
    }

    @Test
    void createImage() throws Exception {
        Image image = new Image();
        when(imageService.save(any(Image.class))).thenReturn(image);

        mockMvc.perform(post("/images")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(image.getId()));
    }

    @Test
    void deleteImage() throws Exception {
        UUID id = UUID.randomUUID();
        
        when(imageService.existsById(id)).thenReturn(true);
        doNothing().when(imageService).deleteById(id);

        mockMvc.perform(delete("/images/{id}", id))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deleteImageNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        
        doNothing().when(imageService).deleteById(id);

        mockMvc.perform(delete("/images/{id}", id))
                .andExpect(status().isNotFound());
    }
}
