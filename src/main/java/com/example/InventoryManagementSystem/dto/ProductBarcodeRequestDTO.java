package com.example.InventoryManagementSystem.dto;


import lombok.Data;

@Data
public class ProductBarcodeRequestDTO {

    private String productId;
    private String barcode;
}