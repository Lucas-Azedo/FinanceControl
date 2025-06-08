package com.example.FinanceControl.exception.businessExceptions;

import com.example.FinanceControl.exception.BusinessException;

public class EmailAlreadyExistsException extends BusinessException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
