package pl.edu.pjwstk.s32410.library.api.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pl.edu.pjwstk.s32410.library.api.exceptions.book.BookNotFoundException;
import pl.edu.pjwstk.s32410.library.api.exceptions.customer.CustomerNotFoundException;
import pl.edu.pjwstk.s32410.library.api.exceptions.employee.EmployeeNotFoundException;
import pl.edu.pjwstk.s32410.library.api.exceptions.rental.RentalConflictingDatesException;
import pl.edu.pjwstk.s32410.library.api.exceptions.rental.RentalIncorrectDatesException;
import pl.edu.pjwstk.s32410.library.api.repository.RentalRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Customer;
import pl.edu.pjwstk.s32410.library.shared.model.Employee;
import pl.edu.pjwstk.s32410.library.shared.model.Rental;
import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private EmployeeService employees;
    
    @Autowired
    private StorageBookService storage;
    
    @Autowired
    private CustomerService customers;
    
    public boolean exists(Rental rental) {
    	return rental != null &&  existsById(rental.getId());
    }
    
    public boolean existsById(UUID id) {
    	return rentalRepository.existsById(id);
    }
    
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Cacheable(value = "rental", key = "#id", unless = "#result == null")
    public Optional<Rental> findById(UUID id) {
        return rentalRepository.findById(id);
    }

    @CacheEvict(value = "rental", key = "#rental.id")
    public Rental save(Rental rental) {
    	Employee empolyee = rental.getEmployee();
    	Customer customer = rental.getCustomer();
    	StorageBook book = rental.getBook();
    	Date start = rental.getStart();
    	Date end = rental.getEnd();
    	
    	if(!employees.exists(empolyee)) throw new EmployeeNotFoundException();
    	if(!customers.exists(customer)) throw new CustomerNotFoundException();
    	if(!storage.exists(book)) throw new BookNotFoundException();
    	if(!checkDates(start, end)) throw new RentalIncorrectDatesException();
    	
    	if(!canRent(book.getId(), start, end)) throw new RentalConflictingDatesException();
    	
        return rentalRepository.save(rental);
    }

    @CacheEvict(value = "rental", key = "#id")
    public void deleteById(UUID id) {
        rentalRepository.deleteById(id);
    }

    public List<Rental> findByCustomerId(UUID customerId) {
        return rentalRepository.findByCustomerId(customerId);
    }

    public List<Rental> findByBookReferenceId(UUID referenceId) {
        return rentalRepository.findByBookReferenceId(referenceId);
    }
    
    public List<Rental> findByBookId(UUID bookId) {
        return rentalRepository.findByBookId(bookId);
    }

    public List<Rental> findByStartBetween(Date startDate, Date endDate) {
        return rentalRepository.findByStartBetween(startDate, endDate);
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
    
    public List<Rental> findConflictingRentals(UUID bookId, Date start, Date end) {
    	return rentalRepository.findConflictingRentals(bookId, start, end);
    }
    
    public boolean canRent(UUID bookId, Date start, Date end) {
    	return findConflictingRentals(bookId, start, end).size() == 0;
    }
    
    private boolean checkDates(Date start, Date end) {
    	return (start != null && end != null && start.before(end));
    }
}
