package org.example.budgetmanager.models.domain;


import jakarta.persistence.*;
import lombok.*;
import org.example.budgetmanager.models.enums.Category;
import org.example.budgetmanager.models.enums.ExpenseType;
import org.example.budgetmanager.models.enums.Status;

import java.math.BigDecimal;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RecurringItem extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    private String serviceName;

    private String cronExpression;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE; // ACTIVE, PAUSED, CANCELLED

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    private User user;

}
