package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.Repository.PurchaseItemRepository;
import com.example.InventoryManagementSystem.Repository.PurchaseRepository;
import com.example.InventoryManagementSystem.Repository.SupplierRepository;
import com.example.InventoryManagementSystem.Repository.UserRepository;
import com.example.InventoryManagementSystem.dto.PurchaseItemRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseResponseDto;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.model.Purchase;
import com.example.InventoryManagementSystem.model.PurchaseItem;
import com.example.InventoryManagementSystem.model.Supplier;
import com.example.InventoryManagementSystem.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductRepository productRepository;

    // CREATE PURCHASE
    @Override
    public PurchaseResponseDto createPurchase(
            PurchaseRequestDto dto) {

        Purchase purchase = new Purchase();

        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + dto.getSupplierId()));
            purchase.setSupplier(supplier);
        }

        if (dto.getCreatedBy() != null) {
            User user = userRepository.findById(dto.getCreatedBy().longValue())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getCreatedBy()));
            purchase.setCreatedBy(user);
        }

        purchase.setInvoiceNumber(dto.getInvoiceNumber());
        purchase.setTotalAmount(dto.getTotalAmount());
        purchase.setTax(dto.getTax());
        purchase.setPaymentStatus(dto.getPaymentStatus());

        Purchase saved = purchaseRepository.save(purchase);

        savePurchaseItems(saved, dto.getItems());

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

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        return mapToDto(purchase);
    }

    // UPDATE PURCHASE
    @Override
    public PurchaseResponseDto updatePurchase(
            Long id,
            PurchaseRequestDto dto) {

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        if (dto.getSupplierId() != null) {
            Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + dto.getSupplierId()));
            purchase.setSupplier(supplier);
        } else {
            purchase.setSupplier(null);
        }

        if (dto.getCreatedBy() != null) {
            User user = userRepository.findById(dto.getCreatedBy().longValue())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getCreatedBy()));
            purchase.setCreatedBy(user);
        } else {
            purchase.setCreatedBy(null);
        }

        purchase.setInvoiceNumber(dto.getInvoiceNumber());
        purchase.setTotalAmount(dto.getTotalAmount());
        purchase.setTax(dto.getTax());
        purchase.setPaymentStatus(dto.getPaymentStatus());

        Purchase updated = purchaseRepository.save(purchase);

        savePurchaseItems(updated, dto.getItems());

        return mapToDto(updated);
    }

    // DELETE PURCHASE
    @Override
    public void deletePurchase(Long id) {

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        purchaseRepository.delete(purchase);
    }

    private void savePurchaseItems(Purchase purchase, List<PurchaseItemRequestDto> items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        for (PurchaseItemRequestDto itemDto : items) {
            if (itemDto.getProductId() == null) {
                continue;
            }

            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemDto.getProductId()));

            int quantity = itemDto.getQuantity() != null ? itemDto.getQuantity() : 0;
            BigDecimal purchasePrice = itemDto.getPurchasePrice() != null ? itemDto.getPurchasePrice() : BigDecimal.ZERO;
            BigDecimal taxAmount = itemDto.getTaxAmount() != null ? itemDto.getTaxAmount() : BigDecimal.ZERO;

            BigDecimal total = itemDto.getTotal();
            if (total == null) {
                total = purchasePrice.multiply(BigDecimal.valueOf(quantity)).add(taxAmount);
            }

            PurchaseItem purchaseItem = PurchaseItem.builder()
                    .purchase(purchase)
                    .product(product)
                    .quantity(quantity)
                    .purchasePrice(purchasePrice)
                    .taxAmount(taxAmount)
                    .total(total)
                    .build();

            purchaseItemRepository.save(purchaseItem);

            // Update product stock quantity
            int currentStock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
            product.setStockQuantity(currentStock + quantity);
            productRepository.save(product);
        }
    }

    // MAP ENTITY TO DTO
    private PurchaseResponseDto mapToDto(Purchase purchase) {

        String supplierName = purchase.getSupplier() != null ? purchase.getSupplier().getSupplierName() : null;
        String createdByUsername = purchase.getCreatedBy() != null ? purchase.getCreatedBy().getUsername() : null;

        return new PurchaseResponseDto(
                purchase.getPurchaseId(),
                supplierName,
                purchase.getInvoiceNumber(),
                purchase.getPurchaseDate(),
                purchase.getTotalAmount(),
                purchase.getTax(),
                purchase.getPaymentStatus(),
                createdByUsername
        );
    }
}