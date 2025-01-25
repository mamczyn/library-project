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

import pl.edu.pjwstk.s32410.library.api.service.CustomerService;
import pl.edu.pjwstk.s32410.library.shared.model.Customer;



@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void getAllCustomers() throws Exception {
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());
        when(customerService.findAll()).thenReturn(customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(customers.size()));
    }

    @Test
    void getCustomerById() throws Exception {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer();
        when(customerService.findById(id)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customer.getId()));
    }

    @Test
    void createCustomer() throws Exception {
        Customer customer = new Customer();
        when(customerService.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":null}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customer.getId()));
    }

    @Test
    void deleteCustomer() throws Exception {
        UUID id = UUID.randomUUID();
        
        when(customerService.existsById(id)).thenReturn(true);
        doNothing().when(customerService).deleteById(id);

        mockMvc.perform(delete("/customers/{id}", id))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deleteCustomerNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        
        doNothing().when(customerService).deleteById(id);

        mockMvc.perform(delete("/customers/{id}", id))
                .andExpect(status().isNotFound());
    }
}
