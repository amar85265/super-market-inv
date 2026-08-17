package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class InvoiceResponseDto {

    private String invoiceId;

    private String invoiceNumber;

    private String customerId;

    private String counterId;

    private BigDecimal subtotal;

    private BigDecimal discountAmount;

    private BigDecimal taxAmount;

    private BigDecimal grandTotal;

    private BigDecimal paidAmount;

    private BigDecimal balanceAmount;

    private String paymentMethod;

    private String paymentStatus;

    private String createdBy;

    private OffsetDateTime createdAt;
}