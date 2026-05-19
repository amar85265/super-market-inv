package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Dto.SupplierRequest;
import com.example.InventoryManagementSystem.Dto.SupplierResponse;

import java.util.List;

public interface SupplierService {

    SupplierResponse createSupplier(SupplierRequest request);

    SupplierResponse getSupplierById(Long supplierId);

    List<SupplierResponse> getAllSuppliers();

    SupplierResponse updateSupplier(Long supplierId,
                                    SupplierRequest request);

    void deleteSupplier(Long supplierId);
}