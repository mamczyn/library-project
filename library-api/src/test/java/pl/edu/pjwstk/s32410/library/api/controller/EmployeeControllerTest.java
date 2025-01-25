package pl.edu.pjwstk.s32410.library.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pl.edu.pjwstk.s32410.library.api.service.EmployeeService;
import pl.edu.pjwstk.s32410.library.shared.model.Employee;



@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void getAllEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());
        when(employeeService.findAll()).thenReturn(employees);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(employees.size()));
    }

    @Test
    void getEmployeeById() throws Exception {
        UUID id = UUID.randomUUID();
        Employee employee = new Employee();
        when(employeeService.findById(id)).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/employees/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()));
    }

    @Test
    void createEmployee() throws Exception {
        Employee employee = new Employee();
        when(employeeService.save(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()));
    }

    @Test
    void deleteEmployee() throws Exception {
        UUID id = UUID.randomUUID();
        
        when(employeeService.existsById(id)).thenReturn(true);
        doNothing().when(employeeService).deleteById(id);

        mockMvc.perform(delete("/employees/{id}", id))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deleteEmployeeNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        
        doNothing().when(employeeService).deleteById(id);

        mockMvc.perform(delete("/employees/{id}", id))
                .andExpect(status().isNotFound());
    }
}
