package com.cjafet.transactions.service;

import com.cjafet.transactions.domain.customer.Customer;
import com.cjafet.transactions.domain.request.CustomerRequest;
import com.cjafet.transactions.exception.CustomerAvailableLimitException;
import com.cjafet.transactions.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Optional<Customer> findOneById(String id) {
        return repository.findById(Long.valueOf(id));
    }

    public Customer addCustomer(CustomerRequest customerRequest) {
        Customer customer = buildCustomer(customerRequest);
        if(customer.getAvailableCreditLimit() != 1000.0) {
            throw new CustomerAvailableLimitException("Client should have a limit");
        }
        return repository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return repository.save(customer);
    }

    private Customer buildCustomer(CustomerRequest customerRequest) {
        return Customer.builder()
                .availableCreditLimit(1000.0)
                .balance(0.0)
                .documentNumber(customerRequest.getDocumentNumber())
                .build();
    }
}
