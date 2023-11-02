package com.cjafet.transactions.domain.transaction;

import com.cjafet.transactions.domain.customer.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "transaction_id")
    @JsonProperty("transaction_id")
    private Long transactionID;

    private Double amount;

    @Column(name = "operation_type_id")
    @JsonProperty("operation_type_id")
    private Integer operationTypeID;

    @Column(name = "event_date")
    @JsonProperty("event_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant eventDate;

    @ManyToOne
    private Customer customer;



}
