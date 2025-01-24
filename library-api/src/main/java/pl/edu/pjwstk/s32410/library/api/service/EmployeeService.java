package pl.edu.pjwstk.s32410.library.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import pl.edu.pjwstk.s32410.library.api.repository.EmployeeRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Employee;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean exists(Employee employee) {
    	return employee != null &&  existsById(employee.getId());
    }
    
    public boolean existsById(UUID id) {
    	return employeeRepository.existsById(id);
    }
    
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Cacheable(value = "employee", key = "#id", unless = "#result == null")
    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);
    }

    @CacheEvict(value = "employee", key = "#employee.id")
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @CacheEvict(value = "employee", key = "#id")
    public void deleteById(UUID id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> findByName(String name) {
        return employeeRepository.findByName(name);
    }

    public List<Employee> findBySurname(String surname) {
        return employeeRepository.findBySurname(surname);
    }

    public List<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public List<Employee> findByPhoneNumber(String phoneNumber) {
        return employeeRepository.findByPhoneNumber(phoneNumber);
    }
}
