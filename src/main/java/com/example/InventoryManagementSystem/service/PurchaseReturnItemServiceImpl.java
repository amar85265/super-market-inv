package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.PurchaseReturnItemRequestDTO;
import com.example.InventoryManagementSystem.dto.PurchaseReturnItemResponseDTO;
import com.example.InventoryManagementSystem.Repository.PurchaseReturnRepository;
import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.model.PurchaseReturn;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.Repository.PurchaseReturnItemRepository;
import com.example.InventoryManagementSystem.model.PurchaseReturnItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseReturnItemServiceImpl
        implements PurchaseReturnItemService {
    private final PurchaseReturnRepository purchaseReturnRepository;
    private final ProductRepository productRepository;

    private final PurchaseReturnItemRepository repository;

    @Override
    public PurchaseReturnItemResponseDTO createPurchaseReturnItem(
            PurchaseReturnItemRequestDTO requestDTO) {

        PurchaseReturn purchaseReturn = purchaseReturnRepository
                .findById(requestDTO.getPurchaseReturnId())
                .orElseThrow(() ->
                        new RuntimeException("Purchase Return not found"));

        Product product = productRepository
                .findById(requestDTO.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        BigDecimal total = requestDTO.getPrice()
                .multiply(BigDecimal.valueOf(requestDTO.getQuantity()));

        PurchaseReturnItem entity = PurchaseReturnItem.builder()
                .purchaseReturnId(requestDTO.getPurchaseReturnId())
                .productId(requestDTO.getProductId())
                .quantity(requestDTO.getQuantity())
                .price(requestDTO.getPrice())
                .total(total)
                .build();

        PurchaseReturnItem saved = repository.save(entity);

        return mapToResponse(saved);
    }

    @Override
    public PurchaseReturnItemResponseDTO getPurchaseReturnItemById(
            String id) {

        PurchaseReturnItem entity =
                repository.findById(id).orElse(null);

        if (entity == null) {
            return null;
        }

        return mapToResponse(entity);
    }

    @Override
    public List<PurchaseReturnItemResponseDTO>
    getAllPurchaseReturnItems() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseReturnItemResponseDTO updatePurchaseReturnItem(
            String id,
            PurchaseReturnItemRequestDTO requestDTO) {
        purchaseReturnRepository.findById(requestDTO.getPurchaseReturnId())
                .orElseThrow(() ->
                        new RuntimeException("Purchase Return not found"));

        productRepository.findById(requestDTO.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        PurchaseReturnItem entity =
                repository.findById(id).orElse(null);

        if (entity == null) {
            return null;
        }

        BigDecimal total = requestDTO.getPrice()
                .multiply(BigDecimal.valueOf(requestDTO.getQuantity()));

        entity.setPurchaseReturnId(requestDTO.getPurchaseReturnId());
        entity.setProductId(requestDTO.getProductId());
        entity.setQuantity(requestDTO.getQuantity());
        entity.setPrice(requestDTO.getPrice());
        entity.setTotal(total);

        PurchaseReturnItem updated = repository.save(entity);

        return mapToResponse(updated);
    }

    @Override
    public void deletePurchaseReturnItem(String id) {

        PurchaseReturnItem entity =
                repository.findById(id).orElse(null);

        if (entity != null) {
            repository.delete(entity);
        }
    }

    private PurchaseReturnItemResponseDTO mapToResponse(
            PurchaseReturnItem entity) {

        return PurchaseReturnItemResponseDTO.builder()
                .purchaseReturnItemId(entity.getPurchaseReturnItemId())
                .purchaseReturnId(entity.getPurchaseReturnId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .total(entity.getTotal())
                .build();
    }
}