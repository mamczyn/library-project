package pl.edu.pjwstk.s32410.library.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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

import pl.edu.pjwstk.s32410.library.api.service.RentalService;
import pl.edu.pjwstk.s32410.library.shared.model.Rental;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RentalController.class)
class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    @Test
    void getAllRentals() throws Exception {
        List<Rental> rentals = Arrays.asList(new Rental(), new Rental());
        when(rentalService.findAll()).thenReturn(rentals);

        mockMvc.perform(get("/rentals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(rentals.size()));
    }

    @Test
    void getRentalById() throws Exception {
        UUID id = UUID.randomUUID();
        Rental rental = new Rental();
        when(rentalService.findById(id)).thenReturn(Optional.of(rental));

        mockMvc.perform(get("/rentals/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rental.getId()));
    }

    @Test
    void createRental() throws Exception {
        Rental rental = new Rental();
        when(rentalService.save(any(Rental.class))).thenReturn(rental);

        mockMvc.perform(post("/rentals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rental.getId()));
    }

    @Test
    void deleteRental() throws Exception {
        UUID id = UUID.randomUUID();
        
        when(rentalService.existsById(id)).thenReturn(true);
        doNothing().when(rentalService).deleteById(id);

        mockMvc.perform(delete("/rentals/{id}", id))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deleteRentalNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        
        doNothing().when(rentalService).deleteById(id);

        mockMvc.perform(delete("/rentals/{id}", id))
                .andExpect(status().isNotFound());
    }
}
