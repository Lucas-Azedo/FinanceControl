package com.example.FinanceControl.model;

import com.example.FinanceControl.enums.TransactionCategory;
import com.example.FinanceControl.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @UuidGenerator
    private UUID id;
    private String name;
    private BigDecimal amount;
    private LocalDateTime date;
    private String description;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}