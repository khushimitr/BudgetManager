package org.example.budgetmanager.models.domain;


import jakarta.persistence.*;
import lombok.*;
import org.example.budgetmanager.models.enums.Category;
import org.example.budgetmanager.models.enums.ExpenseType;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    private User user;

    private LocalDate date;
}
