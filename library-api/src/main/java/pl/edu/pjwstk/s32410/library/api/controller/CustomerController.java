package pl.edu.pjwstk.s32410.library.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.pjwstk.s32410.library.api.exceptions.customer.CustomerNotFoundException;
import pl.edu.pjwstk.s32410.library.api.service.CustomerService;
import pl.edu.pjwstk.s32410.library.shared.model.Customer;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id) {
        Optional<Customer> customer = customerService.findById(id);
        return ResponseEntity.of(customer);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        customer.setId(null);
    	
    	return customerService.save(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id, @RequestBody Customer customer) {
        if (!customerService.existsById(id)) throw new CustomerNotFoundException();
        
        customer.setId(id);
        return ResponseEntity.ok(customerService.save(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        if (!customerService.existsById(id)) throw new CustomerNotFoundException();
        
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public List<Customer> getCustomersByName(@PathVariable String name) {
        return customerService.findByName(name);
    }

    @GetMapping("/surname/{surname}")
    public List<Customer> getCustomersBySurname(@PathVariable String surname) {
        return customerService.findBySurname(surname);
    }

    @GetMapping("/email/{email}")
    public List<Customer> getCustomersByEmail(@PathVariable String email) {
        return customerService.findByEmail(email);
    }

    @GetMapping("/phone-number/{number}")
    public List<Customer> getCustomersByPhoneNumber(@PathVariable String number) {
        return customerService.findByPhoneNumber(number);
    }
}
