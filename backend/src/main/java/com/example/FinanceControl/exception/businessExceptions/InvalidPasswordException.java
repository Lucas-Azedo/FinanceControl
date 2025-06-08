package com.example.FinanceControl.exception.businessExceptions;

import com.example.FinanceControl.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
