package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.PurchaseRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseResponseDto;

import java.util.List;

public interface PurchaseService {


    // CREATE PURCHASE
    PurchaseResponseDto createPurchase(
            PurchaseRequestDto dto);

    // GET ALL PURCHASES
    List<PurchaseResponseDto> getAllPurchases();

    // GET PURCHASE BY ID
    PurchaseResponseDto getPurchaseById(
            String id);

    // UPDATE PURCHASE
    PurchaseResponseDto updatePurchase(
            String id,
            PurchaseRequestDto dto);

    // DELETE PURCHASE
    void deletePurchase(
            String id);


}
