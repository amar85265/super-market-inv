package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.SalesItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalesItemRepository
        extends JpaRepository<SalesItem, String> {

    // changed from findBySaleIdAndProductId
    Optional<SalesItem> findBySale_SaleIdAndProduct_ProductId(
            String saleId, String productId);

    // changed from findBySaleId
    List<SalesItem> findBySale_SaleId(String saleId);

    Optional<SalesItem> findTopByOrderBySaleItemIdDesc();
}