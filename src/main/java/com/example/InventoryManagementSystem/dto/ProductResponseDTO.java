package com.example.InventoryManagementSystem.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponseDTO {
    
    private Long productId;
    private Long categoryId;
    private String productName;
    private String sku;
    private String barcode;
    private BigDecimal purchasePrice;
    private BigDecimal sellingPrice;
    private BigDecimal gstPercentage;
    private Integer stockQuantity;
    private Integer minimumStock;
    private String unit;
    private String status;
    private LocalDateTime createdAt;
}