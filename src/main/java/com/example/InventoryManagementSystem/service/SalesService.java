package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SalesRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesResponseDTO;

import java.util.List;

public interface SalesService {

    SalesResponseDTO createSale(SalesRequestDTO dto);

    SalesResponseDTO getSaleById(String id);

    List<SalesResponseDTO> getAllSales();

    SalesResponseDTO updateSale(String id, SalesRequestDTO dto);

    void deleteSale(String id);
}