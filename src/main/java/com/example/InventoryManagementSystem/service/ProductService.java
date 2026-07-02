package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.ProductRequestDTO;
import com.example.InventoryManagementSystem.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO dto);

    ProductResponseDTO getProductById(String id);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO updateProduct(String id, ProductRequestDTO dto);

    void deleteProduct(String id);
}