package com.example.InventoryManagementSystem.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class PaymentTransactionResponseDTO {
    private String transactionId;
    private String invoiceId;
    private String paymentMethod;
    private String transactionReference;
    private BigDecimal amount;
    private OffsetDateTime paymentDate;
}