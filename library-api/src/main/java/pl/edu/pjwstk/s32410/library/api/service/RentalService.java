package pl.edu.pjwstk.s32410.library.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s32410.library.api.repository.RentalRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Rental;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> findById(UUID id) {
        return rentalRepository.findById(id);
    }

    public Rental save(Rental rental) {
        return rentalRepository.save(rental);
    }

    public void deleteById(UUID id) {
        rentalRepository.deleteById(id);
    }

    public List<Rental> findByCustomerId(UUID customerId) {
        return rentalRepository.findByCustomerId(customerId);
    }

    public List<Rental> findByBookId(UUID bookId) {
        return rentalRepository.findByBookId(bookId);
    }

    public List<Rental> findByStartBetween(Date startDate, Date endDate) {
        return rentalRepository.findByStartBetween(startDate, endDate);
    }

    public List<Rental> findBySiteId(UUID siteId) {
        return rentalRepository.findBySiteId(siteId);
    }

    public List<Rental> findByEmployeeId(UUID employeeId) {
        return rentalRepository.findByEmployeeId(employeeId);
    }

    public List<Rental> findByBookTitle(String title) {
        return rentalRepository.findByBookTitle(title);
    }

    public List<Rental> findByCustomerName(String name) {
        return rentalRepository.findByCustomerName(name);
    }

    public List<Rental> findByEmployeeName(String name) {
        return rentalRepository.findByEmployeeName(name);
    }

    public List<Rental> findBySiteName(String name) {
        return rentalRepository.findBySiteName(name);
    }

    public List<Rental> findByBookCategory(String category) {
        return rentalRepository.findByBookCategory(category);
    }

    public List<Rental> findByAuthorName(String author) {
        return rentalRepository.findByAuthorName(author);
    }

    public List<Rental> findActiveRentals() {
        return rentalRepository.findActiveRentals();
    }

    public List<Rental> findCompletedRentals() {
        return rentalRepository.findCompletedRentals();
    }

    public List<Rental> findOverdueRentals() {
        return rentalRepository.findOverdueRentals();
    }

    public List<Rental> findByBookDescription(String description) {
        return rentalRepository.findByBookDescription(description);
    }
}
