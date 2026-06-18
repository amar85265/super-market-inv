package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SupplierRequestDTO;
import com.example.InventoryManagementSystem.dto.SupplierResponseDTO;

import java.util.List;

public interface SupplierService {

    SupplierResponseDTO createSupplier(SupplierRequestDTO request);

    SupplierResponseDTO getSupplierById(Long supplierId);

    List<SupplierResponseDTO> getAllSuppliers();

    SupplierResponseDTO updateSupplier(Long supplierId,
                                       SupplierRequestDTO request);

    void deleteSupplier(Long supplierId);
}