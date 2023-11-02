package com.cjafet.transactions.controller;

import com.cjafet.transactions.domain.customer.Customer;
import com.cjafet.transactions.domain.request.CustomerRequest;
import com.cjafet.transactions.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomers(@PathVariable("id") String id) {
        Optional<Customer> customer = customerService.findOneById(id);
        if(customer.isPresent()) {
            return ResponseEntity.ok(customer);
        } else {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        Customer customer = customerService.addCustomer(customerRequest);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

}
