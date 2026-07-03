package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SalesItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesItemResponseDTO;

import java.util.List;

public interface SalesItemService {

    SalesItemResponseDTO createSalesItem(SalesItemRequestDTO dto);

    List<SalesItemResponseDTO> getItemsBySaleId(Long saleId);

    void deleteSalesItem(Long id);
}