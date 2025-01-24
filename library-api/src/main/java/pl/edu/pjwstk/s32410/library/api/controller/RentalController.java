package pl.edu.pjwstk.s32410.library.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pjwstk.s32410.library.api.service.RentalService;
import pl.edu.pjwstk.s32410.library.shared.model.Rental;

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
    	
        return ResponseEntity.of(rental);
    }

    @PostMapping
    public Rental createRental(@RequestBody Rental rental) {
    	rental.setId(null);
    	
    	return rentalService.save(rental);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateRental(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        		.body("Rental updating is not supported! Please delete it first and create a new one!");
    }

    @DeleteMapping("/{id}")
    public void deleteRental(@PathVariable UUID id) {
        rentalService.deleteById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Rental> getRentalsByCustomerId(@PathVariable UUID customerId) {
        return rentalService.findByCustomerId(customerId);
    }

    @GetMapping("/book/{bookId}")
    public List<Rental> getRentalsByBookId(@PathVariable UUID bookId) {
        return rentalService.findByBookId(bookId);
    }

    @GetMapping("/date-range")
    public List<Rental> getRentalsByDateRange(@RequestParam Date startDate, @RequestParam Date endDate) {
        return rentalService.findByStartBetween(startDate, endDate);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Rental> getRentalsByEmployeeId(@PathVariable UUID employeeId) {
        return rentalService.findByEmployeeId(employeeId);
    }

    @GetMapping("/book-title/{title}")
    public List<Rental> getRentalsByBookTitle(@PathVariable String title) {
        return rentalService.findByBookTitle(title);
    }

    @GetMapping("/customer-name/{name}")
    public List<Rental> getRentalsByCustomerName(@PathVariable String name) {
        return rentalService.findByCustomerName(name);
    }

    @GetMapping("/employee-name/{name}")
    public List<Rental> getRentalsByEmployeeName(@PathVariable String name) {
        return rentalService.findByEmployeeName(name);
    }

    @GetMapping("/book-category/{category}")
    public List<Rental> getRentalsByBookCategory(@PathVariable String category) {
        return rentalService.findByBookCategory(category);
    }

    @GetMapping("/author-name/{author}")
    public List<Rental> getRentalsByAuthorName(@PathVariable String author) {
        return rentalService.findByAuthorName(author);
    }

    @GetMapping("/active")
    public List<Rental> getActiveRentals() {
        return rentalService.findActiveRentals();
    }

    @GetMapping("/completed")
    public List<Rental> getCompletedRentals() {
        return rentalService.findCompletedRentals();
    }

    @GetMapping("/overdue")
    public List<Rental> getOverdueRentals() {
        return rentalService.findOverdueRentals();
    }
}
