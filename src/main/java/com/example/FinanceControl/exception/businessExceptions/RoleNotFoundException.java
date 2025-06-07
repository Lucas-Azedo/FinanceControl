package com.example.FinanceControl.exception.businessExceptions;

import com.example.FinanceControl.exception.BusinessException;

public class RoleNotFoundException extends BusinessException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
