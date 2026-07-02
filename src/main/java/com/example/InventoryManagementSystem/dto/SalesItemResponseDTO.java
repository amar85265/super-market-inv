package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesItemResponseDTO {

    private String saleItemId;
    private String saleId;
    private String productId;
    private String productName;
    private Integer quantity;
    private BigDecimal sellingPrice;
    private BigDecimal total;
}