package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemRequestDto {

    @NotNull(message = "Invoice ID is required")
    @NotBlank(message = "Invoice ID is required")
    private String invoiceId;

    @NotNull(message = "Product ID is required")
    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotNull(message = "Quantity is required")
    @DecimalMin(
            value = "0.001",
            message = "Quantity must be greater than 0"
    )
    private BigDecimal quantity;

    @DecimalMin(
            value = "0.00",
            message = "Discount cannot be negative"
    )
    private BigDecimal discount = BigDecimal.ZERO;
}