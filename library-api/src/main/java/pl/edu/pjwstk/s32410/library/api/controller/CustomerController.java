package pl.edu.pjwstk.s32410.library.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.s32410.library.api.service.CustomerService;
import pl.edu.pjwstk.s32410.library.shared.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id, @RequestBody Customer customer) {
        if (!customerService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        customer.setId(id);
        return ResponseEntity.ok(customerService.save(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        if (!customerService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name")
    public List<Customer> getCustomersByName(@RequestParam String name) {
        return customerService.findByName(name);
    }

    @GetMapping("/surname")
    public List<Customer> getCustomersBySurname(@RequestParam String surname) {
        return customerService.findBySurname(surname);
    }

    @GetMapping("/email")
    public List<Customer> getCustomersByEmail(@RequestParam String email) {
        return customerService.findByEmail(email);
    }

    @GetMapping("/phone-number")
    public List<Customer> getCustomersByPhoneNumber(@RequestParam String phoneNumber) {
        return customerService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping("/site/{siteId}")
    public List<Customer> getCustomersBySiteId(@PathVariable UUID siteId) {
        return customerService.findBySiteId(siteId);
    }
}
