package com.example.FinanceControl.exception.businessExceptions;

import com.example.FinanceControl.exception.BusinessException;

public class EmailNotFoundException extends BusinessException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
