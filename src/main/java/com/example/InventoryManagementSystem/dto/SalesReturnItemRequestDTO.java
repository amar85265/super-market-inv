package com.example.InventoryManagementSystem.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesReturnItemRequestDTO {

    private Long salesReturnId;
    private Long productId;

    private Integer quantity;

    private BigDecimal price;
}