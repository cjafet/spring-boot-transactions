package com.cjafet.transactions.repository;

import com.cjafet.transactions.domain.customer.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void shouldSaveNewCustomer() {
        customerRepository.save(Customer.builder()
                .availableCreditLimit(5000.0)
                .balance(0.0)
                .documentNumber("12345678900")
                .build());
        assertThat(customerRepository.count()).isEqualTo(1);

        Optional<Customer> customer = customerRepository.findById(1L);
        if(customer.isPresent()) {
            assertThat(customer.get().getAvailableCreditLimit()).isEqualTo(5000.0);
            assertThat(customer.get().getBalance()).isEqualTo(0.0);
        }
    }


}
