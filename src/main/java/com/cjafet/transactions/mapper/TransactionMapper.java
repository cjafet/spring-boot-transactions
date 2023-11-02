package com.cjafet.transactions.mapper;

import com.cjafet.transactions.domain.request.TransactionRequest;
import com.cjafet.transactions.domain.transaction.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toEntity(TransactionRequest transactionRequest);
}
