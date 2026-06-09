package com.example.InventoryManagementSystem.service;


import com.example.InventoryManagementSystem.dto.SalesRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesResponseDTO;
import com.example.InventoryManagementSystem.model.Sales;
import com.example.InventoryManagementSystem.Repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SalesRepository salesRepository;

    // CREATE SALE
    @Override
    public SalesResponseDTO createSale(SalesRequestDTO dto) {

        Sales sale = new Sales();

        sale.setCustomerId(Long.valueOf(dto.getCustomerId()));
        sale.setCreatedBy(Long.valueOf(dto.getCreatedBy()));
        sale.setInvoiceNumber(dto.getInvoiceNumber());
        sale.setPaymentStatus(dto.getPaymentStatus());
        sale.setTotalAmount(dto.getTotalAmount());

        // important business logic
        sale.setSaleDate(LocalDateTime.now());

        Sales saved = salesRepository.save(sale);

        return mapToDTO(saved);
    }

    // GET BY ID
    @Override
    public SalesResponseDTO getSaleById(Long id) {

        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));

        return mapToDTO(sale);
    }

    // GET ALL
    @Override
    public List<SalesResponseDTO> getAllSales() {

        return salesRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public SalesResponseDTO updateSale(Long id, SalesRequestDTO dto) {

        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));

        sale.setCustomerId(Long.valueOf(dto.getCustomerId()));
        sale.setCreatedBy(Long.valueOf(dto.getCreatedBy()));
        sale.setInvoiceNumber(dto.getInvoiceNumber());
        sale.setPaymentStatus(dto.getPaymentStatus());
        sale.setTotalAmount(dto.getTotalAmount());

        Sales updated = salesRepository.save(sale);

        return mapToDTO(updated);
    }

    // DELETE
    @Override
    public void deleteSale(Long id) {

        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));

        salesRepository.delete(sale);
    }

    // MAPPER METHOD
    private SalesResponseDTO mapToDTO(Sales sale) {

        SalesResponseDTO dto = new SalesResponseDTO();

        dto.setSaleId(sale.getSaleId());
        dto.setCustomerId(Math.toIntExact(sale.getCustomerId()));
        dto.setCreatedBy(Math.toIntExact(sale.getCreatedBy()));
        dto.setInvoiceNumber(sale.getInvoiceNumber());
        dto.setPaymentStatus(sale.getPaymentStatus());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setSaleDate(sale.getSaleDate());

        return dto;
    }
}