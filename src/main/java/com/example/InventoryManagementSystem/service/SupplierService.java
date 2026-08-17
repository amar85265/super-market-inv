package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SupplierRequestDTO;
import com.example.InventoryManagementSystem.dto.SupplierResponseDTO;

import java.util.List;

public interface SupplierService {

    SupplierResponseDTO createSupplier(SupplierRequestDTO request);

    SupplierResponseDTO getSupplierById(String supplierId);

    List<SupplierResponseDTO> getAllSuppliers();

    SupplierResponseDTO updateSupplier(String supplierId,
                                       SupplierRequestDTO request);

    void deleteSupplier(String supplierId);
}