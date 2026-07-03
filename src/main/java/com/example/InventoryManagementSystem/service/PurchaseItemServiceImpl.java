package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.PurchaseItemRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseItemResponseDto;
import com.example.InventoryManagementSystem.Repository.PurchaseItemRepository;
import com.example.InventoryManagementSystem.model.PurchaseItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseItemServiceImpl
        implements PurchaseItemService {

    private final PurchaseItemRepository repository;

    // CREATE PURCHASE ITEM
    @Override
    public PurchaseItemResponseDto createPurchaseItem(
            PurchaseItemRequestDto dto) {

        PurchaseItem item = new PurchaseItem();

        item.setPurchaseId(dto.getPurchaseId());

        item.setProductId(dto.getProductId());

        item.setQuantity(dto.getQuantity());

        item.setPurchasePrice(dto.getPurchasePrice());

        // CALCULATE TOTAL
        BigDecimal total =
                dto.getPurchasePrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        dto.getQuantity()
                                )
                        );

        item.setTotal(total);

        // SAVE
        PurchaseItem saved =
                repository.save(item);

        return mapToDto(saved);
    }

    // GET ALL PURCHASE ITEMS
    @Override
    public List<PurchaseItemResponseDto>
    getAllPurchaseItems() {

        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // GET PURCHASE ITEM BY ID
    @Override
    public PurchaseItemResponseDto
    getPurchaseItemById(Long id) {

        PurchaseItem item =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Item not found"));

        return mapToDto(item);
    }

    // UPDATE PURCHASE ITEM
    @Override
    public PurchaseItemResponseDto
    updatePurchaseItem(
            Long id,
            PurchaseItemRequestDto dto) {

        PurchaseItem item =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Item not found"));

        item.setPurchaseId(dto.getPurchaseId());

        item.setProductId(dto.getProductId());

        item.setQuantity(dto.getQuantity());

        item.setPurchasePrice(dto.getPurchasePrice());

        // RECALCULATE TOTAL
        BigDecimal total =
                dto.getPurchasePrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        dto.getQuantity()
                                )
                        );

        item.setTotal(total);

        PurchaseItem updated =
                repository.save(item);

        return mapToDto(updated);
    }

    // DELETE PURCHASE ITEM
    @Override
    public void deletePurchaseItem(Long id) {

        PurchaseItem item =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Item not found"));

        repository.delete(item);
    }

    // MAP ENTITY TO DTO
    private PurchaseItemResponseDto mapToDto(
            PurchaseItem item) {

        return new PurchaseItemResponseDto(

                item.getPurchaseItemId(),

                item.getPurchaseId(),

                item.getProductId(),

                item.getQuantity(),

                item.getPurchasePrice(),

                item.getTotal()
        );
    }
}