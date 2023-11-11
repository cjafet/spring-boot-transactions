package com.cjafet.transactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidOperationTypeException extends RuntimeException {
    public InvalidOperationTypeException(String message) {
        super(message);
    }
}
