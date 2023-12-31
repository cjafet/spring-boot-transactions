package com.cjafet.transactions.controller;

import com.cjafet.transactions.domain.request.TransactionRequest;
import com.cjafet.transactions.domain.transaction.Transaction;
import com.cjafet.transactions.service.TransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static net.logstash.logback.argument.StructuredArguments.keyValue;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> processTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
        logger.info("Transaction received with {} and {}", keyValue("customerId", transactionRequest.getCustomerID()),
                keyValue("operationTypeId", transactionRequest.getOperationTypeID()));
        Transaction transaction = transactionService.addTransaction(transactionRequest);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
