package com.example.InventoryManagementSystem.dto;


import lombok.Data;

@Data
public class ProductBarcodeResponseDTO {

    private String barcodeId;
    private String productId;
    private String barcode;
}