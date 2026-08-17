package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productId;

    private String categoryId;

    @Column(nullable = false)
    private String productName;

    @Column(unique = true)
    private String sku;

    @Column(unique = true)
    private String barcode;

    private BigDecimal purchasePrice;

    private BigDecimal sellingPrice;

    @Column(nullable = false)
    private BigDecimal gstPercentage = BigDecimal.ZERO;

    private Integer stockQuantity;

    private Integer minimumStock;

    private String unit;

    private String status; // active / inactive

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    // Auto update timestamp on update
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}