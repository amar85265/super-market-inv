package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "payment_transactions")
@Data
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "transaction_reference")
    private String transactionReference;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "payment_date")
    private OffsetDateTime paymentDate;
}