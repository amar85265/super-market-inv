package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SupplierRequestDTO;
import com.example.InventoryManagementSystem.dto.SupplierResponseDTO;
import com.example.InventoryManagementSystem.model.Supplier;
import com.example.InventoryManagementSystem.Repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public SupplierResponseDTO createSupplier(
            SupplierRequestDTO request) {

        Supplier supplier = Supplier.builder()
                .supplierName(request.getSupplierName())
                .contactPerson(request.getContactPerson())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .status(request.getStatus())
                .build();

        Supplier savedSupplier =
                supplierRepository.save(supplier);

        return mapToResponse(savedSupplier);
    }

    @Override
    public List<SupplierResponseDTO> getAllSuppliers() {

        return supplierRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SupplierResponseDTO getSupplierById(String id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier Not Found"));

        return mapToResponse(supplier);
    }

    @Override
    public SupplierResponseDTO updateSupplier(
            String id,
            SupplierRequestDTO request) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier Not Found"));

        supplier.setSupplierName(
                request.getSupplierName());

        supplier.setContactPerson(
                request.getContactPerson());

        supplier.setPhone(
                request.getPhone());

        supplier.setEmail(
                request.getEmail());

        supplier.setAddress(
                request.getAddress());

        supplier.setStatus(
                request.getStatus());

        Supplier updatedSupplier =
                supplierRepository.save(supplier);

        return mapToResponse(updatedSupplier);
    }

    @Override
    public void deleteSupplier(String id) {

        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Supplier Not Found"));

        supplierRepository.delete(supplier);
    }

    private SupplierResponseDTO mapToResponse(
            Supplier supplier) {

        return SupplierResponseDTO.builder()
                .supplierId(supplier.getSupplierId())
                .supplierName(supplier.getSupplierName())
                .contactPerson(supplier.getContactPerson())
                .phone(supplier.getPhone())
                .email(supplier.getEmail())
                .address(supplier.getAddress())
                .status(supplier.getStatus())
                .createdAt(supplier.getCreatedAt())
                .build();
    }
}