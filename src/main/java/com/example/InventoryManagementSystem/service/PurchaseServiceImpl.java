package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.PurchaseRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseResponseDto;
import com.example.InventoryManagementSystem.model.Purchase;
import com.example.InventoryManagementSystem.Repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Override
    public PurchaseResponseDto createPurchase(PurchaseRequestDto dto) {

        Purchase purchase = new Purchase();

        purchase.setSupplierId(dto.getSupplierId());
        purchase.setInvoiceNumber(dto.getInvoiceNumber());
        purchase.setTotalAmount(dto.getTotalAmount());
        purchase.setPaymentStatus(dto.getPaymentStatus());
        purchase.setCreatedBy(dto.getCreatedBy());

        Purchase saved = purchaseRepository.save(purchase);

        return mapToDto(saved);
    }

    @Override
    public List<PurchaseResponseDto> getAllPurchases() {

        return purchaseRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public PurchaseResponseDto getPurchaseById(Long id) {

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        return mapToDto(purchase);
    }

    @Override
    public PurchaseResponseDto updatePurchase(Long id, PurchaseRequestDto dto) {

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        purchase.setSupplierId(dto.getSupplierId());
        purchase.setInvoiceNumber(dto.getInvoiceNumber());
        purchase.setTotalAmount(dto.getTotalAmount());
        purchase.setPaymentStatus(dto.getPaymentStatus());
        purchase.setCreatedBy(dto.getCreatedBy());

        Purchase updated = purchaseRepository.save(purchase);

        return mapToDto(updated);
    }

    @Override
    public void deletePurchase(Long id) {

        purchaseRepository.deleteById(id);
    }

    private PurchaseResponseDto mapToDto(Purchase purchase) {

        return new PurchaseResponseDto(
                purchase.getPurchaseId(),
                purchase.getSupplierId(),
                purchase.getInvoiceNumber(),
                purchase.getPurchaseDate(),
                purchase.getTotalAmount(),
                purchase.getPaymentStatus(),
                purchase.getCreatedBy()
        );
    }
}