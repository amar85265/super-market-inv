package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseReturnItemResponseDTO {

    private Integer purchaseReturnItemId;

    private Integer purchaseReturnId;

    private Long productId;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal total;
}