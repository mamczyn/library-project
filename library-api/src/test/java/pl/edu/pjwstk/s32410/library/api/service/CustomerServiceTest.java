package pl.edu.pjwstk.s32410.library.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.edu.pjwstk.s32410.library.api.repository.CustomerRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Customer;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExists() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        when(customerRepository.existsById(customer.getId())).thenReturn(true);
        assertTrue(customerService.exists(customer));
    }

    @Test
    void testFindAll() {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        assertEquals(customers, customerService.findAll());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer();
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        assertEquals(Optional.of(customer), customerService.findById(id));
    }

    @Test
    void testSave() {
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);
        assertEquals(customer, customerService.save(customer));
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(customerRepository).deleteById(id);
        customerService.deleteById(id);
        verify(customerRepository, times(1)).deleteById(id);
    }
}
