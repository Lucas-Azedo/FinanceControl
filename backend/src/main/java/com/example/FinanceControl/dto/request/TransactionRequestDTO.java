package com.example.FinanceControl.dto.request;

import com.example.FinanceControl.enums.TransactionCategory;
import com.example.FinanceControl.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

    @NotNull(message = "Quantidade é obrigatória")
    private BigDecimal amount;

    @NotBlank(message = "Descrição é obrigatória")
    private String description;

    @NotNull(message = "Tipo de transação é obrigatório")
    private TransactionType type;

    @NotNull(message = "Categoria de transação é obrigatória")
    private TransactionCategory category;
}
