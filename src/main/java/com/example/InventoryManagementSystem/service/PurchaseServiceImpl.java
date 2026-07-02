package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.PurchaseRepository;
import com.example.InventoryManagementSystem.Repository.SupplierRepository;
import com.example.InventoryManagementSystem.Repository.UserRepository;
import com.example.InventoryManagementSystem.dto.PurchaseRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseResponseDto;
import com.example.InventoryManagementSystem.model.Purchase;
import com.example.InventoryManagementSystem.model.Supplier;
import com.example.InventoryManagementSystem.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;

    // Auto-generate PUR-001, PUR-002...
    private String generatePurchaseId() {
        Optional<Purchase> last =
                purchaseRepository.findTopByOrderByPurchaseIdDesc();

        if (last.isEmpty()) return "PUR-001";

        String lastId = last.get().getPurchaseId();
        int num = 0;

        if (lastId != null && lastId.startsWith("PUR-")) {
            num = Integer.parseInt(lastId.substring(4));
        }

        return String.format("PUR-%03d", num + 1);
    }

    @Override
    public PurchaseResponseDto createPurchase(
            PurchaseRequestDto dto) {

        Supplier supplier = supplierRepository
                .findById(Long.valueOf(dto.getSupplierId()))
                .orElseThrow(() ->
                        new RuntimeException("Supplier not found"));

        User user = userRepository
                .findById(Long.valueOf(dto.getCreatedBy()))
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Purchase purchase = new Purchase();

        // Auto-generate purchase ID
        purchase.setPurchaseId(generatePurchaseId());

        purchase.setSupplier(supplier);
        purchase.setCreatedBy(user);
        purchase.setInvoiceNumber(dto.getInvoiceNumber());
        purchase.setTotalAmount(dto.getTotalAmount());
        purchase.setTax(dto.getTax());
        purchase.setPaymentStatus(dto.getPaymentStatus());

        return mapToDto(purchaseRepository.save(purchase));
    }

    @Override
    public List<PurchaseResponseDto> getAllPurchases() {
        return purchaseRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public PurchaseResponseDto getPurchaseById(String id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Purchase not found with id: " + id));
        return mapToDto(purchase);
    }

    @Override
    public PurchaseResponseDto updatePurchase(
            String id, PurchaseRequestDto dto) {

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Purchase not found with id: " + id));

        Supplier supplier = supplierRepository
                .findById(Long.valueOf(dto.getSupplierId()))
                .orElseThrow(() ->
                        new RuntimeException("Supplier not found"));

        User user = userRepository
                .findById(Long.valueOf(dto.getCreatedBy()))
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        purchase.setSupplier(supplier);
        purchase.setCreatedBy(user);
        purchase.setInvoiceNumber(dto.getInvoiceNumber());
        purchase.setTotalAmount(dto.getTotalAmount());
        purchase.setTax(dto.getTax());
        purchase.setPaymentStatus(dto.getPaymentStatus());

        return mapToDto(purchaseRepository.save(purchase));
    }

    @Override
    public void deletePurchase(String id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Purchase not found with id: " + id));
        purchaseRepository.delete(purchase);
    }

    private PurchaseResponseDto mapToDto(Purchase purchase) {
        return PurchaseResponseDto.builder()
                .purchaseId(purchase.getPurchaseId())
                .SupplierId(purchase.getSupplier() != null
                        ? String.valueOf(purchase.getSupplier().getSupplierId())
                        : null)
                .supplierName(purchase.getSupplier() != null
                        ? purchase.getSupplier().getSupplierName()
                        : "No Supplier")
                .invoiceNumber(purchase.getInvoiceNumber())
                .purchaseDate(purchase.getPurchaseDate())
                .totalAmount(purchase.getTotalAmount())
                .tax(purchase.getTax())
                .paymentStatus(purchase.getPaymentStatus())
                .createdBy(purchase.getCreatedBy() != null
                        ? purchase.getCreatedBy().getUsername()
                        : "No User")
                .build();
    }
}