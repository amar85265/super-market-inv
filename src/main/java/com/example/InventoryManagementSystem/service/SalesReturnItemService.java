package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SalesReturnItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesReturnItemResponseDTO;

import java.util.List;

public interface SalesReturnItemService {

    SalesReturnItemResponseDTO createItem(SalesReturnItemRequestDTO dto);

    List<SalesReturnItemResponseDTO> getByReturnId(Long salesReturnId);

    void deleteItem(Long id);
}