package com.cjafet.transactions.controller;

import com.cjafet.transactions.domain.customer.Customer;
import com.cjafet.transactions.domain.request.TransactionRequest;
import com.cjafet.transactions.domain.transaction.Transaction;
import com.cjafet.transactions.service.TransactionService;
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

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;
    @Mock
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;
    private static final String TRANSACTION_API_PATH = "/api/transactions";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void shouldCreateNewTransactionForExistingCustomer() throws Exception {
        when(transactionService.addTransaction(any(TransactionRequest.class))).thenReturn(buildTransactionResponse());
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(TRANSACTION_API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"customer_id\": 1, \"operation_type_id\": 4, \"amount\": 123.45 }")
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.transaction_id").exists())
                .andExpect(jsonPath("$.transaction_id").isNotEmpty())
                .andExpect(jsonPath("$.amount").value(123.45))
                .andExpect(jsonPath("$.operation_type_id").value(4))
                .andExpect(jsonPath("$.event_date").exists())
                .andExpect(jsonPath("$.event_date").isNotEmpty())
                .andExpect(jsonPath("$.customer.customer_id").value(1))
                .andExpect(jsonPath("$.customer.document_number").value("12345678900"))
                .andReturn();
    }

    private Transaction buildTransactionResponse() {
        return Transaction.builder()
                .transactionID(Long.valueOf(1))
                .amount(123.45)
                .operationTypeID(4)
                .eventDate(Instant.now())
                .customer(Customer.builder()
                        .customerID(Long.valueOf(1))
                        .documentNumber("12345678900")
                        .build())
                .build();
    }
}
