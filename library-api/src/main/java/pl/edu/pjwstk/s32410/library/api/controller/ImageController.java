package pl.edu.pjwstk.s32410.library.api.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pjwstk.s32410.library.api.service.ImageService;
import pl.edu.pjwstk.s32410.library.shared.model.Employee;
import pl.edu.pjwstk.s32410.library.shared.model.Image;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;
    
    @GetMapping
    public List<Image> getAllImages() {
        return imageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable UUID id) {
        Optional<Image> image = imageService.findById(id);
        if (image.isPresent()) {
            byte[] imageBytes = Base64.getDecoder().decode(image.get().getBase64Data());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Image createImage(@RequestBody Image image) {
    	image.setId(null);
    	image.setDateOfPublication(Date.from(Instant.now()));
        
    	return imageService.save(image);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> createImage(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        		.body("Image updating is not supported!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID id) {
        if (!imageService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        imageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

