package com.cjafet.transactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class LimitNotAvailableException extends RuntimeException {
    public LimitNotAvailableException(String message) {
        super(message);
    }
}
