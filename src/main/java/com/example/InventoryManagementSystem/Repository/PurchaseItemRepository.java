package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseItemRepository
        extends JpaRepository<PurchaseItem, String> {


    Optional<PurchaseItem> findTopByOrderByPurchaseItemIdDesc();

    // Find purchase items by product to reduce remaining quantity
    List<PurchaseItem> findByProduct_ProductIdAndRemainingQuantityGreaterThan(
            String productId, Integer quantity);



}