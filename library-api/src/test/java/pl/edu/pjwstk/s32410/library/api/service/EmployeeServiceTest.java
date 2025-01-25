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

import pl.edu.pjwstk.s32410.library.api.repository.EmployeeRepository;
import pl.edu.pjwstk.s32410.library.shared.model.Employee;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExists() {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        when(employeeRepository.existsById(employee.getId())).thenReturn(true);
        assertTrue(employeeService.exists(employee));
    }

    @Test
    void testFindAll() {
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());
        when(employeeRepository.findAll()).thenReturn(employees);
        assertEquals(employees, employeeService.findAll());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Employee employee = new Employee();
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        assertEquals(Optional.of(employee), employeeService.findById(id));
    }

    @Test
    void testSave() {
        Employee employee = new Employee();
        when(employeeRepository.save(employee)).thenReturn(employee);
        assertEquals(employee, employeeService.save(employee));
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();
        doNothing().when(employeeRepository).deleteById(id);
        employeeService.deleteById(id);
        verify(employeeRepository, times(1)).deleteById(id);
    }
}
