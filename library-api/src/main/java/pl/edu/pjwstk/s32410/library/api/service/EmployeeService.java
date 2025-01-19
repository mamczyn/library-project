package pl.edu.pjwstk.s32410.library.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s32410.library.api.repository.EmployeeRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

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

    public List<Employee> findBySiteId(UUID siteId) {
        return employeeRepository.findBySiteId(siteId);
    }
}
