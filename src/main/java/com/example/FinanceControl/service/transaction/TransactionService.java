package com.example.FinanceControl.service.transaction;

import com.example.FinanceControl.dto.request.TransactionRequestDTO;
import com.example.FinanceControl.dto.response.TransactionResponseDTO;
import com.example.FinanceControl.exception.IdNotFoundException;
import com.example.FinanceControl.model.Transaction;
import com.example.FinanceControl.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto){
        Transaction transaction = new Transaction();

        transaction.setAmount(dto.getAmount());
        transaction.setDescription(dto.getDescription());
        transaction.setType(dto.getType());
        transaction.setCategory(dto.getCategory());

        transactionRepository.save(transaction);

        return new TransactionResponseDTO(transaction.getId(), transaction.getAmount(), transaction.getDescription(),transaction.getDate(), transaction.getType(), transaction.getCategory());
    }

    public TransactionResponseDTO getTransactionById(UUID id){
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Id nao encontrado"));

        return new TransactionResponseDTO(transaction.getId(), transaction.getAmount(), transaction.getDescription(),transaction.getDate(), transaction.getType(), transaction.getCategory());
    }

    public List<TransactionResponseDTO> getAllTransaction(){
        return transactionRepository.findAll()
                .stream()
                .map(transaction -> new TransactionResponseDTO(transaction.getId(), transaction.getAmount(), transaction.getDescription(),transaction.getDate(), transaction.getType(), transaction.getCategory()))
                .toList();
    }

    public void deleteTransaction(UUID id){
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Id nao encontrado"));

        transactionRepository.delete(transaction);
    }
}
