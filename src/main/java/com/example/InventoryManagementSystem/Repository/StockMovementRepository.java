package com.example.InventoryManagementSystem.Repository;

import com.example.InventoryManagementSystem.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockMovementRepository
        extends JpaRepository<StockMovement, String> {

    List<StockMovement> findByProduct_ProductId(String productId);

    Optional<StockMovement> findTopByOrderByMovementIdDesc();
}