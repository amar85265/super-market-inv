package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseItemRequestDto {

    private Integer purchaseId;

    private Integer productId;

    private Integer quantity;

    private BigDecimal purchasePrice;

    private BigDecimal taxAmount;

    private BigDecimal total;
}