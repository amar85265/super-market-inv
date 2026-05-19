package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Dto.PurchaseRequestDto;
import com.example.InventoryManagementSystem.Dto.PurchaseResponseDto;

import java.util.List;

public interface PurchaseService {

    PurchaseResponseDto createPurchase(PurchaseRequestDto dto);

    List<PurchaseResponseDto> getAllPurchases();

    PurchaseResponseDto getPurchaseById(Long id);

    PurchaseResponseDto updatePurchase(Long id, PurchaseRequestDto dto);

    void deletePurchase(Long id);
}