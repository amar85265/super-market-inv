package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Dto.PurchaseItemRequestDto;
import com.example.InventoryManagementSystem.Dto.PurchaseItemResponseDto;

import java.util.List;

public interface PurchaseItemService {

    PurchaseItemResponseDto createPurchaseItem(
            PurchaseItemRequestDto dto);

    List<PurchaseItemResponseDto> getAllPurchaseItems();

    PurchaseItemResponseDto getPurchaseItemById(Long id);

    PurchaseItemResponseDto updatePurchaseItem(
            Long id,
            PurchaseItemRequestDto dto);

    void deletePurchaseItem(Long id);
}