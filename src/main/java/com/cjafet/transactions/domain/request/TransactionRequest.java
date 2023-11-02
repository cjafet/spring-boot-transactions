package com.cjafet.transactions.domain.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequest {
    @JsonAlias({"customer_id"})
    @NotNull(message = "Customer ID can not be empty or null")
    private Long customerID;

    @JsonAlias({"operation_type_id"})
    @NotNull(message = "Operation Type ID can not be empty or null")
    private Integer operationTypeID;

    @NotNull(message = "Amount can not be empty or null")
    private Double amount;
}
