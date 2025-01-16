
package pl.edu.pjwstk.s32410.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.s32410.library.api.service.RentalService;
import pl.edu.pjwstk.s32410.library.shared.model.Rental;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable UUID id) {
        Optional<Rental> rental = rentalService.findById(id);
        return rental.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Rental createRental(@RequestBody Rental rental) {
        return rentalService.save(rental);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable UUID id, @RequestBody Rental rental) {
        if (!rentalService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        rental.setId(id);
        return ResponseEntity.ok(rentalService.save(rental));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable UUID id) {
        if (!rentalService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        rentalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
