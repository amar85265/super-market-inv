package com.example.InventoryManagementSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseReturnItemRequestDTO {

    private Integer purchaseReturnId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    private Integer quantity;

    private BigDecimal price;
}