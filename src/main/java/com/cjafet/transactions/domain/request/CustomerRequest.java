package com.cjafet.transactions.domain.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {
    @JsonAlias({"document_number"})
    @NotEmpty(message = "Document number can not be empty or null")
    private String documentNumber;
}
