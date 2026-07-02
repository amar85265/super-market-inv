package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.StockMovementRequest;
import com.example.InventoryManagementSystem.dto.StockMovementResponse;

import java.util.List;

public interface StockMovementService {

    StockMovementResponse createStockMovement(
            StockMovementRequest request);

    StockMovementResponse getStockMovementById(
            String movementId);

    List<StockMovementResponse> getAllStockMovements();

    List<StockMovementResponse> getByProductId(
            String productId);

    void deleteStockMovement(String movementId);
}