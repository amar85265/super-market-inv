package com.example.InventoryManagementSystem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesItemRequestDTO {

    private Long saleId;
    private Long productId;

    private Integer quantity;

    private BigDecimal sellingPrice;
}