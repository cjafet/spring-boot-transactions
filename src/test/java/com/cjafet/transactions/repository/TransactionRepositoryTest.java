package com.cjafet.transactions.repository;

import com.cjafet.transactions.domain.customer.Customer;
import com.cjafet.transactions.domain.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void shouldSaveNewTransaction() {
        transactionRepository.save(buildTransaction());
        assertThat(transactionRepository.count()).isEqualTo(1);
    }

    private Transaction buildTransaction() {
        Customer customer = Customer.builder().documentNumber("12345678900").build();
        Customer savedCustomer = customerRepository.save(customer);
        return Transaction.builder()
                .operationTypeID(1)
                .customer(savedCustomer)
                .amount(123.45)
                .build();
    }
}
