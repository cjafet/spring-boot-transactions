package com.cjafet.transactions.repository;

import com.cjafet.transactions.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer save(Customer customer);
}
