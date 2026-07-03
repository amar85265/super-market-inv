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
    @Column(name = "sale_item_id", length = 20)
    private String saleItemId;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sales sale;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "selling_price", precision = 12, scale = 2)
    private BigDecimal sellingPrice;

    @Column(precision = 12, scale = 2)
    private BigDecimal total;
}