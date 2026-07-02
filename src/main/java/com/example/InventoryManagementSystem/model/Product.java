package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "sku"),
                @UniqueConstraint(columnNames = "barcode")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String productId;

    private Long categoryId;

    @Column(nullable = false,length = 200)
    private String productName;

    @Column(nullable = false)
    private String sku;

    @Column(nullable = false)
    private String barcode;

    @Column(precision = 12,scale = 2)
    private BigDecimal purchasePrice;

    @Column(precision = 12,scale = 2)
    private BigDecimal sellingPrice;

    private Integer stockQuantity;

    private Integer minimumStock;

    private String unit;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}