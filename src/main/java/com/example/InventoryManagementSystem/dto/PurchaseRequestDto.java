package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseRequestDto {

    private Long supplierId;

    private String invoiceNumber;

    private BigDecimal totalAmount;

    private String paymentStatus;

    private Long createdBy;
}