
package edu.ism.payment_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "factures")
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String reference;

    @Column(nullable = false, length = 50)
    private String walletCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ServiceName serviceName;

    @Column(nullable = false, length = 30)
    private String unite;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FactureStatus status;

    @Column(nullable = false)
    private LocalDate billingMonth;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDateTime paidAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void beforeInsert() {
        if (status == null) {
            status = FactureStatus.UNPAID;
        }

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}