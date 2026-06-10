package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class PaymentTransactionRequestDTO {

    private Long invoiceId;
    private String paymentMethod;
    private String transactionReference;
    private BigDecimal amount;
    private OffsetDateTime paymentDate;
}