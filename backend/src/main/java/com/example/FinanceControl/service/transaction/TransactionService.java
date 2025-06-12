package com.example.FinanceControl.service.transaction;

import com.example.FinanceControl.dto.request.TransactionRequestDTO;
import com.example.FinanceControl.dto.response.TransactionResponseDTO;
import com.example.FinanceControl.exception.businessExceptions.EmailNotFoundException;
import com.example.FinanceControl.exception.businessExceptions.IdNotFoundException;
import com.example.FinanceControl.model.Transaction;
import com.example.FinanceControl.model.User;
import com.example.FinanceControl.repository.TransactionRepository;
import com.example.FinanceControl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto) {
        Transaction transaction = new Transaction();

        User user = getAuthenticatedUser();

        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(LocalDateTime.now());
        transaction.setType(dto.getType());
        transaction.setCategory(dto.getCategory());
        transaction.setUser(user);

        transactionRepository.save(transaction);

        return toDTO(transaction);
    }

    public TransactionResponseDTO getTransactionById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Id nao encontrado"));

        User user = getAuthenticatedUser();

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Acesso negado à transação.");
        }


        return toDTO(transaction);
    }

    public List<TransactionResponseDTO> getAllTransaction() {
        User user = getAuthenticatedUser();

        return transactionRepository.findByUser(user)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public void deleteTransaction(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Id nao encontrado"));

        User user = getAuthenticatedUser();

        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Acesso negado à transação.");
        }


        transactionRepository.delete(transaction);
    }

    private TransactionResponseDTO toDTO(Transaction transaction) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getCategory()
        );
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Email nao encontrado."));
    }
}