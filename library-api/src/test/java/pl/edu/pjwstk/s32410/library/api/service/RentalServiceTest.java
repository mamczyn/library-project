package pl.edu.pjwstk.s32410.library.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.edu.pjwstk.s32410.library.api.repository.RentalRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Customer;
import pl.edu.pjwstk.s32410.library.shared.model.Employee;
import pl.edu.pjwstk.s32410.library.shared.model.Rental;
import pl.edu.pjwstk.s32410.library.shared.model.StorageBook;

public class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private StorageBookService storageBookService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private RentalService rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExists() {
        Rental rental = new Rental();
        rental.setId(UUID.randomUUID());
        when(rentalRepository.existsById(rental.getId())).thenReturn(true);
        assertTrue(rentalService.exists(rental));
    }

    @Test
    void testFindAll() {
        List<Rental> rentals = Arrays.asList(new Rental(), new Rental());
        when(rentalRepository.findAll()).thenReturn(rentals);
        assertEquals(rentals, rentalService.findAll());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Rental rental = new Rental();
        when(rentalRepository.findById(id)).thenReturn(Optional.of(rental));
        assertEquals(Optional.of(rental), rentalService.findById(id));
    }

    @Test
    void testRental() {
    	Rental rental = new Rental();
        rental.setId(UUID.randomUUID());
        rental.setEmployee(new Employee());
        rental.setCustomer(new Customer());
        rental.setBook(new StorageBook());
        rental.setStart(LocalDate.now());
        rental.setEnd(LocalDate.now().plusDays(30));

        when(employeeService.exists(any(Employee.class))).thenReturn(true);
        when(customerService.exists(any(Customer.class))).thenReturn(true);
        when(storageBookService.exists(any(StorageBook.class))).thenReturn(true);
        when(rentalRepository.save(rental)).thenReturn(rental);
        when(rentalRepository.findByBookId(any(UUID.class)))
            .thenReturn(List.of());

        assertEquals(rental, rentalService.save(rental));
        verify(rentalRepository).save(rental);
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(rentalRepository).deleteById(id);
        rentalService.deleteById(id);
        verify(rentalRepository, times(1)).deleteById(id);
    }
}
