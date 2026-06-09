package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseRequestDto {

    private Integer supplierId;

    private String invoiceNumber;

    private BigDecimal totalAmount;

    private BigDecimal tax;

    private String paymentStatus;

    private Integer createdBy;


}
