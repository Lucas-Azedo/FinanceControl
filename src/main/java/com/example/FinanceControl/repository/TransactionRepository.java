package com.example.FinanceControl.repository;

import com.example.FinanceControl.model.Transaction;
import com.example.FinanceControl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByUser(User user);
}
