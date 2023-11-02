package com.cjafet.transactions.controller;

import com.cjafet.transactions.domain.customer.Customer;
import com.cjafet.transactions.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    private static final String CUSTOMER_API_PATH = "/api/customers";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void shouldReturnCustomerForExistingID() throws Exception {
        when(customerService.findOneById(anyString())).thenReturn(buildCustomerResponse());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_API_PATH + "/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customer_id").value(1))
                .andExpect(jsonPath("$.document_number").value("12345678900"))
                .andReturn();
    }

    @Test
    public void shouldReturnStatusNotFound() throws Exception {
        when(customerService.findOneById(anyString())).thenReturn(buildCustomerNotFoundResponse());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_API_PATH + "/{id}", "1001"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    private Optional<Customer> buildCustomerNotFoundResponse() {
        return Optional.empty();
    }

    private Optional<Customer> buildCustomerResponse() {
        return Optional.of(Customer.builder()
                .customerID(Long.valueOf(1))
                .documentNumber("12345678900")
                .build());
    }
}
