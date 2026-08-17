package com.example.InventoryManagementSystem.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseReturnItemResponseDTO {

    private String purchaseReturnItemId;

    private String purchaseReturnId;

    private String productId;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal total;
}