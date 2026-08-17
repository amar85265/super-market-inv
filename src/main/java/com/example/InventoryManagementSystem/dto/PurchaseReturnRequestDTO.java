package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.*;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseReturnRequestDTO {

    @NotNull(message = "Purchase ID is required")
    private Long purchaseId;

    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Return quantity is required")
    @Min(value = 1, message = "Return quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Total amount is required")
    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Total amount cannot be negative"
    )
    private BigDecimal totalAmount;

    private String notes;
}