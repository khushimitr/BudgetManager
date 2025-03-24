package org.example.budgetmanager.models.domain;


import jakarta.persistence.*;
import lombok.*;
import org.example.budgetmanager.models.enums.ExpenseType;

import java.math.BigDecimal;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class Expense extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @ManyToOne
    private Category category;

    private ExpenseType expenseType;

    private BigDecimal amount;

    @ManyToOne
    private User user;
}
