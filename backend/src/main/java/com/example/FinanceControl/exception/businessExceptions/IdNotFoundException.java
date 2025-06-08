package com.example.FinanceControl.exception.businessExceptions;

import com.example.FinanceControl.exception.BusinessException;

public class IdNotFoundException extends BusinessException {
    public IdNotFoundException(String message) {
        super(message);
    }
}
