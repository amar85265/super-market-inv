package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponseDto {

    private Long purchaseId;

    private Long supplierId;

    private String invoiceNumber;

    private OffsetDateTime purchaseDate;

    private BigDecimal totalAmount;

    private String paymentStatus;

    private Long createdBy;
}