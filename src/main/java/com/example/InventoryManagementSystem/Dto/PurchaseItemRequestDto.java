package com.example.InventoryManagementSystem.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseItemRequestDto {

    private Long purchaseId;

    private Long productId;

    private Integer quantity;

    private BigDecimal purchasePrice;
}