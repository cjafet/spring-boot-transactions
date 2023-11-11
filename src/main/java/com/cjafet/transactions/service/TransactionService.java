package com.cjafet.transactions.service;

import com.cjafet.transactions.domain.customer.Customer;
import com.cjafet.transactions.domain.request.TransactionRequest;
import com.cjafet.transactions.domain.transaction.Transaction;
import com.cjafet.transactions.exception.CustomerNotFoundException;
import com.cjafet.transactions.exception.LimitNotAvailableException;
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
            Double balanceWithLimit = getCustomerBalance(customer) + getCustomerLimit(customer);
            if(transactionRequest.getAmount() <= balanceWithLimit) {
                updateBalance(transactionRequest, customer);
                Transaction transaction = buildTransaction(transactionRequest, customer);
                return repository.save(transaction);
            } else {
                throw new LimitNotAvailableException("Limit not available");
            }
        } else {
            throw new CustomerNotFoundException(String.format("CustomerID=%s not found", transactionRequest.getCustomerID()));
        }
    }

    private void updateBalance(TransactionRequest tx, Optional<Customer> customer) {
        Double updatedBalance = 0.0;
        if(customer.isPresent() && tx.getOperationTypeID().equals(1)) {
            updatedBalance = removeFromBalance(tx, customer);
        } else if (customer.isPresent() && tx.getOperationTypeID().equals(4)) {
            updatedBalance = addToBalance(tx, customer);
        }
            customer.get().setBalance(updatedBalance);
            customerService.updateCustomer(customer.get());
        System.out.println("New balance" + updatedBalance);
    }

    private Double addToBalance(TransactionRequest tx, Optional<Customer> customer) {
        return customer.get().getBalance() + tx.getAmount();
    }

    private Double removeFromBalance(TransactionRequest tx, Optional<Customer> customer) {
        return customer.get().getBalance() - tx.getAmount();
    }

    private Double getCustomerBalance(Optional<Customer> customer) {
        if(customer.isPresent()) {
            return customer.get().getBalance();
        }
        return 0.0;
    }

    private Double getCustomerLimit(Optional<Customer> customer) {
        if(customer.isPresent()) {
            return customer.get().getAvailableCreditLimit();
        }
        return 0.0;
    }

    private Transaction buildTransaction(TransactionRequest transactionRequest, Optional<Customer> customer) {
       return Transaction.builder()
                .amount(verifyTransactionType(transactionRequest))
                .customer(customer.get())
                .operationTypeID(transactionRequest.getOperationTypeID())
                .eventDate(Instant.now())
                .build();
    }

    private Double verifyTransactionType(TransactionRequest tx) {
        if(tx.getOperationTypeID().equals(1)) {
            tx.setAmount(-tx.getAmount());
        }
        return tx.getAmount();
    }


}
