package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sales")
@Getter
@Setter
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    private Long customerId;
    private Long createdBy;

    private String invoiceNumber;
    private String paymentStatus;

    private BigDecimal totalAmount;

    private LocalDateTime saleDate = LocalDateTime.now();
}