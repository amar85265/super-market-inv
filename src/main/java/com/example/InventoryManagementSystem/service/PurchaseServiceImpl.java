package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.*;
import com.example.InventoryManagementSystem.dto.PurchaseItemRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseRequestDto;

import com.example.InventoryManagementSystem.dto.PurchaseResponseDto;
import com.example.InventoryManagementSystem.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl
        implements PurchaseService {


    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductRepository productRepository;

    @Override
    public PurchaseResponseDto createPurchase(
            PurchaseRequestDto dto) {

        Supplier supplier =
                supplierRepository.findById(
                                Long.valueOf(dto.getSupplierId()))
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplier not found"));

        if (purchaseRepository.existsBySupplierAndInvoiceNumber(
                supplier,
                dto.getInvoiceNumber())) {

            throw new RuntimeException(
                    "Invoice number already exists for this supplier.");
        }


        User user =
                userRepository.findById(
                                Long.valueOf(dto.getCreatedBy()))
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        Purchase purchase = new Purchase();

        purchase.setSupplier(supplier);
        purchase.setCreatedBy(user);
        purchase.setInvoiceNumber(dto.getInvoiceNumber());
        purchase.setTotalAmount(dto.getTotalAmount());
        purchase.setTax(dto.getTax());
        purchase.setPaymentStatus(dto.getPaymentStatus());

        Purchase saved = purchaseRepository.save(purchase);

        savePurchaseItems(saved, dto.getItems());

        return mapToDto(saved);
    }

    @Override
    public List<PurchaseResponseDto>
    getAllPurchases() {

        return purchaseRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public PurchaseResponseDto
    getPurchaseById(Long id) {

        Purchase purchase =
                purchaseRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase not found"));


        return mapToDto(purchase);
    }

    @Override
    public PurchaseResponseDto updatePurchase(
            Long id,
            PurchaseRequestDto dto) {

        Purchase purchase =
                purchaseRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase not found"));

        // Get old purchase items
        List<PurchaseItem> oldItems =
                purchaseItemRepository.findByPurchaseId(id.intValue());

        // Restore old stock
        for (PurchaseItem item : oldItems) {

            Product product =
                    productRepository.findById(
                                    String.valueOf(item.getProductId()))
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Product not found"));

            product.setStockQuantity(
                    product.getStockQuantity()
                            - item.getQuantity());

            productRepository.save(product);
        }

        // Delete old purchase items
        purchaseItemRepository.deleteAll(oldItems);

        // Get Supplier
        Supplier supplier =
                supplierRepository.findById(
                                Long.valueOf(dto.getSupplierId()))
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplier not found"));

        if (purchaseRepository.existsBySupplierAndInvoiceNumber(
                supplier,
                dto.getInvoiceNumber())) {

            throw new RuntimeException(
                    "Invoice number already exists for this supplier.");
        }

        // Get User
        User user =
                userRepository.findById(
                                Long.valueOf(dto.getCreatedBy()))
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        // Update Purchase
        purchase.setSupplier(supplier);
        purchase.setCreatedBy(user);
        purchase.setInvoiceNumber(dto.getInvoiceNumber());
        purchase.setTotalAmount(dto.getTotalAmount());
        purchase.setTax(dto.getTax());
        purchase.setPaymentStatus(dto.getPaymentStatus());
        purchase.setPurchaseDate(LocalDateTime.now());

        Purchase updated =
                purchaseRepository.save(purchase);

        // Save new purchase items and increase stock
        savePurchaseItems(updated, dto.getItems());

        return mapToDto(updated);
    }
    @Override
    public void deletePurchase(Long id) {

        purchaseRepository.deleteById(id);
    }

    private void savePurchaseItems(Purchase purchase,
                                   List<PurchaseItemRequestDto> items) {

        for (PurchaseItemRequestDto item : items) {

            PurchaseItem purchaseItem = getPurchaseItem(purchase, item);

            purchaseItemRepository.save(purchaseItem);

            Product product = productRepository.findById(
                            String.valueOf(item.getProductId()))
                    .orElseThrow(() ->
                            new RuntimeException("Product not found"));

            product.setStockQuantity(
                    product.getStockQuantity() + item.getQuantity());

            productRepository.save(product);
        }
    }

    private static PurchaseItem getPurchaseItem(Purchase purchase, PurchaseItemRequestDto item) {
        PurchaseItem purchaseItem = new PurchaseItem();

        purchaseItem.setPurchaseId(purchase.getPurchaseId().intValue());
        purchaseItem.setProductId(item.getProductId());
        purchaseItem.setQuantity(item.getQuantity());
        purchaseItem.setPurchasePrice(item.getPurchasePrice());
        purchaseItem.setTaxAmount(item.getTaxAmount());

        BigDecimal total = item.getPurchasePrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()))
                .add(item.getTaxAmount());

        purchaseItem.setTotal(total);
        return purchaseItem;
    }

    private PurchaseResponseDto mapToDto(
            Purchase purchase) {


        return PurchaseResponseDto.builder()
                .purchaseId(purchase.getPurchaseId())
                .supplierName(
                        purchase.getSupplier() != null
                                ? purchase.getSupplier().getSupplierName()
                                : "No Supplier")
                .invoiceNumber(purchase.getInvoiceNumber())
                .purchaseDate(purchase.getPurchaseDate())
                .totalAmount(purchase.getTotalAmount())
                .tax(purchase.getTax())
                .paymentStatus(purchase.getPaymentStatus())
                .createdBy(
                        purchase.getCreatedBy() != null
                                ? purchase.getCreatedBy().getUsername()
                                : "No User")
                .build();


    }


}
