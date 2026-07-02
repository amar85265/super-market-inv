package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository
        extends JpaRepository<Product, String> {

    boolean existsByBarcode(String barcode);

    boolean existsBySku(String sku);

    Optional<Product> findTopByOrderByProductIdDesc();
}