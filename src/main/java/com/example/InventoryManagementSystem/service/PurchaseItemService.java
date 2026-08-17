package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.PurchaseItemRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseItemResponseDto;

import java.util.List;

public interface PurchaseItemService {

    PurchaseItemResponseDto createPurchaseItem(
            PurchaseItemRequestDto request);

    List<PurchaseItemResponseDto> getAllPurchaseItems();

    PurchaseItemResponseDto getPurchaseItemById(
            String purchaseItemId);

    PurchaseItemResponseDto updatePurchaseItem(
            String purchaseItemId,
            PurchaseItemRequestDto request);

    void deletePurchaseItem(String purchaseItemId);
}