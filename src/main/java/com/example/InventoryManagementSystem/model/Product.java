package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be greater than 0")
    private Long categoryId;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100,
            message = "Product name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String productName;

    @NotBlank(message = "SKU is required")
    @Size(max = 50,
            message = "SKU cannot exceed 50 characters")
    @Column(unique = true)
    private String sku;

    @NotBlank(message = "Barcode is required")
    @Pattern(
            regexp = "^[0-9]{8,14}$",
            message = "Barcode must contain only digits and be between 8 and 14 digits"
    )
    @Column(unique = true, nullable = false)
    private String barcode;

    @NotNull(message = "Purchase price is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Purchase price must be greater than 0")
    private BigDecimal purchasePrice;

    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Selling price must be greater than 0")
    private BigDecimal sellingPrice;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0,
            message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @NotNull(message = "Minimum stock is required")
    @Min(value = 0,
            message = "Minimum stock cannot be negative")
    private Integer minimumStock;

    @NotBlank(message = "Unit is required")
    @Pattern(
            regexp = "PCS|KG|GRAM|LITER|ML|BOX|PACK",
            message = "Unit must be PCS, KG, GRAM, LITER, ML, BOX, or PACK"
    )
    private String unit;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "ACTIVE|INACTIVE",
            message = "Status must be ACTIVE or INACTIVE"
    )
    private String status;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}