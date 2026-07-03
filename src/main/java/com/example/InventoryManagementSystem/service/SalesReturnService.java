package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SalesReturnRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesReturnResponseDTO;

import java.util.List;

public interface SalesReturnService {

    SalesReturnResponseDTO createReturn(SalesReturnRequestDTO dto);

    SalesReturnResponseDTO getById(String id);

    List<SalesReturnResponseDTO> getAll();

    SalesReturnResponseDTO updateReturn(String id, SalesReturnRequestDTO dto);

    void delete(String id);
}