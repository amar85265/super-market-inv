package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemResponseDto {

    private Long purchaseItemId;

    private Long purchaseId;

    private Long productId;

    private Integer quantity;

    private BigDecimal purchasePrice;

    private BigDecimal total;
}