package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.PurchaseReturnItemRequestDTO;
import com.example.InventoryManagementSystem.dto.PurchaseReturnItemResponseDTO;

import java.util.List;

public interface PurchaseReturnItemService {

    PurchaseReturnItemResponseDTO createPurchaseReturnItem(
            PurchaseReturnItemRequestDTO requestDTO
    );

    PurchaseReturnItemResponseDTO getPurchaseReturnItemById(String id);

    List<PurchaseReturnItemResponseDTO> getAllPurchaseReturnItems();

    PurchaseReturnItemResponseDTO updatePurchaseReturnItem(
            String id,
            PurchaseReturnItemRequestDTO requestDTO
    );

    void deletePurchaseReturnItem(String id);
}