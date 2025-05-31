package com.example.FinanceControl.dto.request;

import com.example.FinanceControl.enums.*;
import com.example.FinanceControl.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    BigDecimal amount;
    String description;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;
}
