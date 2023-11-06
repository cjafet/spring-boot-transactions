package com.cjafet.transactions.repository;

import com.cjafet.transactions.domain.customer.Customer;
import com.cjafet.transactions.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void shouldSaveNewCustomer() {
        customerRepository.save(Customer.builder().documentNumber("12345678900").build());
        assertThat(customerRepository.count()).isEqualTo(1);
    }
}
