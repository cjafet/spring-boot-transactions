package com.cjafet.transactions.service;

import com.cjafet.transactions.domain.customer.Customer;
import com.cjafet.transactions.domain.request.CustomerRequest;
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
        return repository.save(customer);
    }

    private Customer buildCustomer(CustomerRequest customerRequest) {
        return Customer.builder().documentNumber(customerRequest.getDocumentNumber()).build();
    }
}
