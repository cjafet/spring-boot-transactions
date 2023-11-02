package com.cjafet.transactions.domain.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "account_id")
    @JsonProperty("customer_id")
    private Long customerID;

    @NotEmpty(message = "Document number can not be empty or null")
    @Column(name = "document_number")
    @JsonProperty("document_number")
    private String documentNumber;

//    @OneToMany(mappedBy = "customer")
//    private List<Transaction> tranactions;


}
