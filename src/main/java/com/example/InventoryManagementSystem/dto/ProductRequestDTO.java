package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {

    private Long categoryId;

    @NotBlank(message = "Product Name is required")
    private String productName;

    @NotBlank(message = "SKU is required")
    private String sku;

    @NotBlank(message = "Barcode is required")
    private String barcode;

    @NotNull
    @Positive
    private BigDecimal purchasePrice;

    @NotNull
    @Positive
    private BigDecimal sellingPrice;

    @NotNull
    @Min(0)
    private Integer stockQuantity;

    @NotNull
    @Min(0)
    private Integer minimumStock;

    private String unit;

    private String status;
}