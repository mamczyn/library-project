package pl.edu.pjwstk.s32410.library.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pl.edu.pjwstk.s32410.library.api.repository.CustomerRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Customer;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public boolean exists(Customer customer) {
    	return customer != null &&  existsById(customer.getId());
    }
    
    public boolean existsById(UUID id) {
    	return customerRepository.existsById(id);
    }
    
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    
    @Cacheable(value = "customer", key = "#id", unless = "#result == null")
    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    @CacheEvict(value = "customer", key = "#customer.id")
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @CacheEvict(value = "customer", key = "#id")
    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    public List<Customer> findBySurname(String surname) {
        return customerRepository.findBySurname(surname);
    }

    public List<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> findByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }
}
