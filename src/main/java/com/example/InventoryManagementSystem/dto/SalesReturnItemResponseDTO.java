package com.example.InventoryManagementSystem.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SalesReturnItemResponseDTO {
    private String salesReturnItemId;
    private String salesReturnId;
    private String productId;
    private String productName;   // human‑readable
    private Integer quantity;
    private BigDecimal price;     // original sale unit price
    private BigDecimal total;
}