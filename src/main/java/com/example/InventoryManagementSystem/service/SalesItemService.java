package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SalesItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesItemResponseDTO;

import java.util.List;

public interface SalesItemService {

    SalesItemResponseDTO createSalesItem(SalesItemRequestDTO dto);

    SalesItemResponseDTO updateSalesItem(String id, SalesItemRequestDTO dto);

    List<SalesItemResponseDTO> getAllSalesItems();

    SalesItemResponseDTO getSalesItemById(String id);

    List<SalesItemResponseDTO> getItemsBySaleId(String saleId);

    void deleteSalesItem(String id);
}