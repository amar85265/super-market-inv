package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.PurchaseItemRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseItemResponseDto;
import com.example.InventoryManagementSystem.model.PurchaseItem;
import com.example.InventoryManagementSystem.Repository.PurchaseItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseItemServiceImpl
        implements PurchaseItemService {

    private final PurchaseItemRepository purchaseItemRepository;

    @Override
    public PurchaseItemResponseDto createPurchaseItem(
            PurchaseItemRequestDto request) {

        BigDecimal total =
                request.getPurchasePrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        request.getQuantity()))
                        .add(request.getTaxAmount());

        PurchaseItem item = PurchaseItem.builder()
                .purchaseId(request.getPurchaseId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .purchasePrice(request.getPurchasePrice())
                .taxAmount(request.getTaxAmount())
                .total(total)
                .build();

        return mapToResponse(
                purchaseItemRepository.save(item));
    }

    @Override
    public List<PurchaseItemResponseDto>
    getAllPurchaseItems() {

        return purchaseItemRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PurchaseItemResponseDto
    getPurchaseItemById(String purchaseItemId) {

        PurchaseItem item =
                purchaseItemRepository.findById(
                                purchaseItemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Item Not Found"));

        return mapToResponse(item);
    }

    @Override
    public PurchaseItemResponseDto updatePurchaseItem(
            String purchaseItemId,
            PurchaseItemRequestDto request) {

        PurchaseItem item =
                purchaseItemRepository.findById(
                                purchaseItemId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Item Not Found"));

        BigDecimal total =
                request.getPurchasePrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        request.getQuantity()))
                        .add(request.getTaxAmount());

        item.setPurchaseId(request.getPurchaseId());
        item.setProductId(request.getProductId());
        item.setQuantity(request.getQuantity());
        item.setPurchasePrice(request.getPurchasePrice());
        item.setTaxAmount(request.getTaxAmount());
        item.setTotal(total);

        return mapToResponse(
                purchaseItemRepository.save(item));
    }

    @Override
    public void deletePurchaseItem(String purchaseItemId) {

        purchaseItemRepository.deleteById(
                purchaseItemId);
    }

    private PurchaseItemResponseDto mapToResponse(
            PurchaseItem item) {

        return PurchaseItemResponseDto.builder()
                .purchaseItemId(item.getPurchaseItemId())
                .purchaseId(item.getPurchaseId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .purchasePrice(item.getPurchasePrice())
                .taxAmount(item.getTaxAmount())
                .total(item.getTotal())
                .build();
    }
}