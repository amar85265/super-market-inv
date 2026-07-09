package com.example.InventoryManagementSystem.Repository;


import com.example.InventoryManagementSystem.model.ProductBarcode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductBarcodeRepository extends JpaRepository<ProductBarcode, String> {

    List<ProductBarcode> findByProductId(String productId);

    Optional<ProductBarcode> findByBarcode(String barcode);
}