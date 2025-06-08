package com.example.FinanceControl.controller;

import com.example.FinanceControl.dto.request.TransactionRequestDTO;
import com.example.FinanceControl.dto.response.TransactionResponseDTO;
import com.example.FinanceControl.service.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "Gerenciamento de transações financeiras")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Criar nova transação")
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @RequestBody @Valid TransactionRequestDTO dto) {
        return ResponseEntity.ok(transactionService.createTransaction(dto));
    }

    @Operation(summary = "Listar todas as transações")
    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransaction() {
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }

    @Operation(summary = "Obter transação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(
            @Parameter(description = "ID da transação", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @Operation(summary = "Excluir transação por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(
            @Parameter(description = "ID da transação", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
