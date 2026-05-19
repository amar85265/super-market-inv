package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    private Long supplierId;

    private String invoiceNumber;

    private OffsetDateTime purchaseDate;

    private BigDecimal totalAmount;

    private String paymentStatus;

    private Long createdBy;

    @PrePersist
    public void prePersist() {
        this.purchaseDate = OffsetDateTime.now();
    }
}