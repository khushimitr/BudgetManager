package org.example.budgetmanager.models.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.budgetmanager.models.enums.Category;
import org.example.budgetmanager.models.enums.ExpenseType;

import java.math.BigDecimal;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class Loan extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    private Category category;

    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private User toUser;

    @Column(nullable = false)
    private String username;
}
