package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "sales_return_items")
@Getter
@Setter
public class SalesReturnItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long salesReturnItemId;

    private Long salesReturnId;
    private Long saleId;
    private Long productId;

    private Integer quantity;

    private BigDecimal price;
    private BigDecimal total;
}