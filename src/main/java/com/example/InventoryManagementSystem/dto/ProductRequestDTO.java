package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be greater than 0")
    private Long categoryId;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Pattern(
            regexp = "^(?!0+$)[A-Za-z0-9&()\\-., ]+$",
            message = "Product name contains invalid characters"
    )
    private String productName;

    @NotBlank(message = "SKU is required")
    @Size(min = 8, max = 30, message = "SKU must be between 8 and 30 characters")
    @Pattern(
            regexp = "^[A-Za-z0-9]+$",
            message = "SKU must contain only letters and numbers"
    )
    private String sku;

    @NotBlank(message = "Barcode is required")
    @Pattern(
            regexp = "^\\d{8,20}$",
            message = "Barcode must contain 8 to 20 digits"
    )
    private String barcode;

    @NotNull(message = "Purchase price is required")
    @DecimalMin(value = "0.01", inclusive = true,
            message = "Purchase price must be greater than 0")
    @Digits(integer = 8, fraction = 2,
            message = "Purchase price can have up to 8 digits and 2 decimal places")
    private BigDecimal purchasePrice;

    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.01", inclusive = true,
            message = "Selling price must be greater than 0")
    @Digits(integer = 8, fraction = 2,
            message = "Selling price can have up to 8 digits and 2 decimal places")
    private BigDecimal sellingPrice;

    private BigDecimal gstPercentage;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @NotNull(message = "Minimum stock is required")
    @Min(value = 0, message = "Minimum stock cannot be negative")
    private Integer minimumStock;

    @NotBlank(message = "Unit is required")
    private String unit;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "^(ACTIVE|INACTIVE)$",
            message = "Status must be ACTIVE or INACTIVE"
    )
    private String status;
}