package com.example.InventoryManagementSystem.service;


import com.example.InventoryManagementSystem.dto.ProductBarcodeRequestDTO;
import com.example.InventoryManagementSystem.dto.ProductBarcodeResponseDTO;

import java.util.List;

public interface ProductBarcodeService {

    ProductBarcodeResponseDTO createBarcode(ProductBarcodeRequestDTO request);

    ProductBarcodeResponseDTO updateBarcode(String id, ProductBarcodeRequestDTO request);

    List<ProductBarcodeResponseDTO> getAll();

    List<ProductBarcodeResponseDTO> getByProductId(String productId);

    ProductBarcodeResponseDTO getByBarcode(String barcode);

    void deleteBarcode(String barcodeId);
}