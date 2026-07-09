package com.example.InventoryManagementSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "product_barcodes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductBarcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String barcodeId;

    @NotNull(message = "Product ID is required")
    @Column(nullable = false)
    private String productId;

    @NotBlank(message = "Barcode is required")
    @Size(min = 8, max = 50, message = "Barcode must be between 8 and 50 characters")
    @Column(unique = true, nullable = false, length = 50)
    private String barcode;
}