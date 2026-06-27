package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.*;
import com.example.InventoryManagementSystem.dto.PurchaseReturnItemRequestDTO;
import com.example.InventoryManagementSystem.dto.PurchaseReturnRequestDTO;
import com.example.InventoryManagementSystem.dto.PurchaseReturnResponseDTO;
import com.example.InventoryManagementSystem.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

    private final PurchaseReturnRepository purchaseReturnRepository;
    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final PurchaseReturnItemRepository purchaseReturnItemRepository;
    private final ProductRepository productRepository;

    @Override
    public PurchaseReturnResponseDTO createPurchaseReturn(
            PurchaseReturnRequestDTO requestDTO) {

        Purchase purchase = purchaseRepository.findById(requestDTO.getPurchaseId())
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        Supplier supplier = supplierRepository.findById(requestDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        PurchaseReturn entity = PurchaseReturn.builder()
                .purchaseId(Math.toIntExact(requestDTO.getPurchaseId()))
                .supplierId(Math.toIntExact(requestDTO.getSupplierId()))
                .returnDate(LocalDateTime.now())
                .totalAmount(requestDTO.getTotalAmount())
                .notes(requestDTO.getNotes())
                .build();

        PurchaseReturn saved = purchaseReturnRepository.save(entity);

        savePurchaseReturnItems(saved, requestDTO.getItems());

        return mapToResponse(saved);
    }

    @Override
    public PurchaseReturnResponseDTO getPurchaseReturnById(Integer id) {

        PurchaseReturn entity = purchaseReturnRepository.findById(id)
                .orElse(null);

        if (entity == null) {
            return null;
        }

        return mapToResponse(entity);
    }

    @Override
    public List<PurchaseReturnResponseDTO> getAllPurchaseReturns() {

        return purchaseReturnRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseReturnResponseDTO updatePurchaseReturn(
            Integer id,
            PurchaseReturnRequestDTO requestDTO) {

        PurchaseReturn entity = purchaseReturnRepository.findById(id)
                .orElse(null);

        if (entity == null) {
            return null;
        }

        purchaseRepository.findById(requestDTO.getPurchaseId())
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        supplierRepository.findById(requestDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        entity.setPurchaseId(Math.toIntExact(requestDTO.getPurchaseId()));
        entity.setSupplierId(Math.toIntExact(requestDTO.getSupplierId()));
        entity.setTotalAmount(requestDTO.getTotalAmount());
        entity.setNotes(requestDTO.getNotes());

        PurchaseReturn updated = purchaseReturnRepository.save(entity);

        return mapToResponse(updated);
    }

    @Override
    public void deletePurchaseReturn(Integer id) {

        PurchaseReturn entity = purchaseReturnRepository.findById(id)
                .orElse(null);

        if (entity != null) {
            purchaseReturnRepository.delete(entity);
        }
    }
    private void savePurchaseReturnItems(
            PurchaseReturn purchaseReturn,
            List<PurchaseReturnItemRequestDTO> items) {

        for (PurchaseReturnItemRequestDTO item : items) {

            PurchaseReturnItem returnItem = new PurchaseReturnItem();

            returnItem.setPurchaseReturnId(
                    purchaseReturn.getPurchaseReturnId());

            returnItem.setProductId(item.getProductId().intValue());
            returnItem.setQuantity(item.getQuantity());
            returnItem.setPrice(item.getPrice());

            returnItem.setTotal(
                    item.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity())));

            purchaseReturnItemRepository.save(returnItem);

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found"));

            product.setStockQuantity(
                    product.getStockQuantity() - item.getQuantity());

            productRepository.save(product);
        }
    }

    private PurchaseReturnResponseDTO mapToResponse(
            PurchaseReturn entity) {

        return PurchaseReturnResponseDTO.builder()
                .purchaseReturnId(entity.getPurchaseReturnId())
                .purchaseId(Long.valueOf(entity.getPurchaseId()))
                .supplierId(Long.valueOf(entity.getSupplierId()))
                .returnDate(entity.getReturnDate())
                .totalAmount(entity.getTotalAmount())
                .notes(entity.getNotes())
                .build();
    }
}