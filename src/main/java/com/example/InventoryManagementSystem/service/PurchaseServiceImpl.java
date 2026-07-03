package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.PurchaseRepository;
import com.example.InventoryManagementSystem.dto.PurchaseRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseResponseDto;
import com.example.InventoryManagementSystem.model.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    // CREATE PURCHASE
    @Override
    public PurchaseResponseDto createPurchase(
            PurchaseRequestDto dto) {

        Purchase purchase = new Purchase();

        purchase.setSupplierId(
                dto.getSupplierId());

        purchase.setInvoiceNumber(
                dto.getInvoiceNumber());

        purchase.setTotalAmount(
                dto.getTotalAmount());

        purchase.setTax(
                dto.getTax());

        purchase.setPaymentStatus(
                dto.getPaymentStatus());

        purchase.setCreatedBy(
                dto.getCreatedBy());

        Purchase saved =
                purchaseRepository.save(purchase);

        return mapToDto(saved);
    }

    // GET ALL PURCHASES
    @Override
    public List<PurchaseResponseDto> getAllPurchases() {

        return purchaseRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // GET PURCHASE BY ID
    @Override
    public PurchaseResponseDto getPurchaseById(
            Long id) {

        Purchase purchase =
                purchaseRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase not found"));

        return mapToDto(purchase);
    }

    // UPDATE PURCHASE
    @Override
    public PurchaseResponseDto updatePurchase(
            Long id,
            PurchaseRequestDto dto) {

        Purchase purchase =
                purchaseRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase not found"));

        purchase.setSupplierId(
                dto.getSupplierId());

        purchase.setInvoiceNumber(
                dto.getInvoiceNumber());

        purchase.setTotalAmount(
                dto.getTotalAmount());

        purchase.setTax(
                dto.getTax());

        purchase.setPaymentStatus(
                dto.getPaymentStatus());

        purchase.setCreatedBy(
                dto.getCreatedBy());

        Purchase updated =
                purchaseRepository.save(purchase);

        return mapToDto(updated);
    }

    // DELETE PURCHASE
    @Override
    public void deletePurchase(Long id) {

        Purchase purchase =
                purchaseRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase not found"));

        purchaseRepository.delete(purchase);
    }

    // MAP ENTITY TO DTO
    private PurchaseResponseDto mapToDto(
            Purchase purchase) {

        return new PurchaseResponseDto(
                purchase.getPurchaseId(),
                purchase.getSupplierId()
                        .getSupplierName(),
                purchase.getInvoiceNumber(),
                purchase.getPurchaseDate(),
                purchase.getTotalAmount(),
                purchase.getTax(),
                purchase.getPaymentStatus(),
                purchase.getCreatedBy()
                        .getUsername()
        );
    }
}