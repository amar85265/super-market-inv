package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SalesReturnItemRequestDTO {

    @NotNull(message = "Sales Return Id is required")
    private String salesReturnId;

    @NotNull(message = "Sale Id is required")
    private String saleId;   // NEW – for explicit validation

    @NotNull(message = "Product Id is required")
    private String productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;
}