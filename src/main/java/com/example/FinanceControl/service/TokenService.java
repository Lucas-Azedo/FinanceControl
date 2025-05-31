package com.example.FinanceControl.service;

import com.example.FinanceControl.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    public String generateToken(User user) {
        return UUID.randomUUID().toString(); // JWT no futuro
    }
}
