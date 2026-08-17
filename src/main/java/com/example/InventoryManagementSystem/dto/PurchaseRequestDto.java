package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PurchaseRequestDto {

    private String supplierId;

    private List<PurchaseItemRequestDto> items;

    private String invoiceNumber;

    private BigDecimal totalAmount;

    private BigDecimal tax;

    private String paymentStatus;

    private String createdBy;


}
