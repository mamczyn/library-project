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

import pl.edu.pjwstk.s32410.library.api.exceptions.employee.EmployeeNotFoundException;
import pl.edu.pjwstk.s32410.library.api.service.EmployeeService;
import pl.edu.pjwstk.s32410.library.shared.model.Employee;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable UUID id) {
        Optional<Employee> employee = employeeService.findById(id);
        return ResponseEntity.of(employee);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
    	employee.setId(null);
    	
    	return employeeService.save(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable UUID id, @RequestBody Employee employee) {
        if (!employeeService.existsById(id)) throw new EmployeeNotFoundException();
        
        employee.setId(id);
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        if (!employeeService.existsById(id)) throw new EmployeeNotFoundException();
        
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public List<Employee> getEmployeesByName(@PathVariable String name) {
        return employeeService.findByName(name);
    }

    @GetMapping("/surname/{surname}")
    public List<Employee> getEmployeesBySurname(@PathVariable String surname) {
        return employeeService.findBySurname(surname);
    }

    @GetMapping("/email/{email}")
    public List<Employee> getEmployeesByEmail(@PathVariable String email) {
        return employeeService.findByEmail(email);
    }

    @GetMapping("/phone-number/{number}")
    public List<Employee> getEmployeesByPhoneNumber(@PathVariable String number) {
        return employeeService.findByPhoneNumber(number);
    }
}
