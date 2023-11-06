package com.cjafet.transactions.service;

import com.cjafet.transactions.domain.customer.Customer;
import com.cjafet.transactions.domain.request.TransactionRequest;
import com.cjafet.transactions.domain.transaction.Transaction;
import com.cjafet.transactions.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    CustomerService customerService;
    @Mock
    TransactionRepository transactionRepository;
    @InjectMocks
    TransactionService transactionService;

    @Test
    void shouldAddTransaction() {
        Optional<Customer> customer = buildCustomer();
        when(customerService.findOneById(anyString())).thenReturn(customer);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(buildTransactionResponse());
        Transaction tx = transactionService.addTransaction(buildTransactionRequest());
        assertThat(tx.getAmount()).isNotNull();
    }

    private Optional<Customer> buildCustomer() {
        return Optional.of(Customer.builder()
                .customerID(1L)
                .documentNumber("12345678900")
                .build());
    }

    private TransactionRequest buildTransactionRequest() {
        return TransactionRequest.builder()
                .customerID(1L)
                .amount(678.99)
                .operationTypeID(1)
                .build();
    }

    private Transaction buildTransactionResponse() {
        return Transaction.builder()
                .transactionID(1L)
                .operationTypeID(1)
                .eventDate(Instant.now())
                .amount(-678.99)
                .build();
    }


}
