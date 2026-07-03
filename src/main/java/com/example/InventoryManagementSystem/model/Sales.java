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
    @Column(name = "sale_id", length = 20)
    private String saleId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "sale_date")
    private LocalDateTime saleDate;

//    @Enumerated(EnumType.STRING)   // stores enum name in DB
//    @Column(name = "payment_status")
//    private SaleStatus paymentStatus;

    @PrePersist
    public void prePersist() {
        this.saleDate = LocalDateTime.now();

    }
}