package pl.edu.pjwstk.s32410.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.s32410.library.api.service.RentalService;
import pl.edu.pjwstk.s32410.library.shared.model.Rental;

import java.util.Date;
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
    public Optional<Rental> getRentalById(@PathVariable UUID id) {
        return rentalService.findById(id);
    }

    @PostMapping
    public Rental createRental(@RequestBody Rental rental) {
        return rentalService.save(rental);
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

    @GetMapping("/site/{siteId}")
    public List<Rental> getRentalsBySiteId(@PathVariable UUID siteId) {
        return rentalService.findBySiteId(siteId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Rental> getRentalsByEmployeeId(@PathVariable UUID employeeId) {
        return rentalService.findByEmployeeId(employeeId);
    }

    @GetMapping("/book-title")
    public List<Rental> getRentalsByBookTitle(@RequestParam String title) {
        return rentalService.findByBookTitle(title);
    }

    @GetMapping("/customer-name")
    public List<Rental> getRentalsByCustomerName(@RequestParam String name) {
        return rentalService.findByCustomerName(name);
    }

    @GetMapping("/employee-name")
    public List<Rental> getRentalsByEmployeeName(@RequestParam String name) {
        return rentalService.findByEmployeeName(name);
    }

    @GetMapping("/site-name")
    public List<Rental> getRentalsBySiteName(@RequestParam String name) {
        return rentalService.findBySiteName(name);
    }

    @GetMapping("/book-category")
    public List<Rental> getRentalsByBookCategory(@RequestParam String category) {
        return rentalService.findByBookCategory(category);
    }

    @GetMapping("/author-name")
    public List<Rental> getRentalsByAuthorName(@RequestParam String author) {
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

    @GetMapping("/book-description")
    public List<Rental> getRentalsByBookDescription(@RequestParam String description) {
        return rentalService.findByBookDescription(description);
    }
}
