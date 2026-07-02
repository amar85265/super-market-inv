package com.example.InventoryManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemResponseDto {

    private String purchaseItemId;

    private String purchaseId;

    private String invoiceNumber;

    private String productId;

    private String productName;

    private Integer quantity;

    private BigDecimal purchasePrice;

    private BigDecimal taxAmount;

    private BigDecimal total;
}