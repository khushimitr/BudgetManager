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
public class Loan extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    private ExpenseType expenseType;

    @ManyToOne
    private Category category;

    private BigDecimal amount;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private User toUser;

    @Column(nullable = false)
    private String username;
}
