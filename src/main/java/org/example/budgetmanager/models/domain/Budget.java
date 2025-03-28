package org.example.budgetmanager.models.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class Budget extends AbstractAuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    private User user;

    @Column(precision = 19, scale = 2)
    private BigDecimal budget;
}

