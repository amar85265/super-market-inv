package com.example.InventoryManagementSystem.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "sales_items")
@Getter
@Setter
public class SalesItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleItemId;

    private Long saleId;

    private Long productId;

    private Integer quantity;

    private BigDecimal sellingPrice;

    private BigDecimal total;
}