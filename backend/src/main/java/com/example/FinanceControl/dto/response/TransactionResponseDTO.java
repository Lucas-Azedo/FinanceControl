package com.example.FinanceControl.dto.response;

import com.example.FinanceControl.enums.TransactionCategory;
import com.example.FinanceControl.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {
    UUID id;
    BigDecimal amount;
    String description;
    Date date;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;


}
