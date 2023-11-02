package com.cjafet.transactions.service;

import com.cjafet.transactions.domain.customer.Customer;
import com.cjafet.transactions.domain.request.TransactionRequest;
import com.cjafet.transactions.domain.transaction.Transaction;
import com.cjafet.transactions.exception.CustomerNotFoundException;
import com.cjafet.transactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private TransactionRepository repository;

    public Transaction addTransaction(TransactionRequest transactionRequest) {
        Optional<Customer> customer = customerService.findOneById(String.valueOf(transactionRequest.getCustomerID()));
        if(customer.isPresent()) {
            Transaction transaction = buildTransaction(transactionRequest, customer);
            return repository.save(transaction);
        } else {
            throw new CustomerNotFoundException();
        }
    }

    private Transaction buildTransaction(TransactionRequest transactionRequest, Optional<Customer> customer) {
        Transaction transaction = Transaction.builder()
                .amount(transactionRequest.getAmount())
                .customer(customer.get())
                .operationTypeID(transactionRequest.getOperationTypeID())
                .eventDate(Instant.now())
                .build();
        return transaction;
    }
}
