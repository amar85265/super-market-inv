package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SalesReturnItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesReturnItemResponseDTO;

import java.util.List;

public interface SalesReturnItemService {

    SalesReturnItemResponseDTO createItem(SalesReturnItemRequestDTO dto);

    List<SalesReturnItemResponseDTO> getAll();

    SalesReturnItemResponseDTO getById(String id);  // required

    List<SalesReturnItemResponseDTO> getByReturnId(String salesReturnId);

    SalesReturnItemResponseDTO updateItem(String id, SalesReturnItemRequestDTO dto);

    void deleteItem(String id);
}