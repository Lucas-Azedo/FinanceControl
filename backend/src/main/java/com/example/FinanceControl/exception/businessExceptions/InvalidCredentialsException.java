package com.example.FinanceControl.exception.businessExceptions;

import com.example.FinanceControl.exception.BusinessException;

public class InvalidCredentialsException extends BusinessException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
