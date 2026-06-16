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

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl
        implements PurchaseService {


    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;

    @Override
    public PurchaseResponseDto createPurchase(
            PurchaseRequestDto dto) {

        Supplier supplier =
                supplierRepository.findById(
                                Long.valueOf(dto.getSupplierId()))
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplier not found"));

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

        Purchase saved =
                purchaseRepository.save(purchase);

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
    public PurchaseResponseDto
    updatePurchase(
            Long id,
            PurchaseRequestDto dto) {

        Purchase purchase =
                purchaseRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase not found"));

        Supplier supplier =
                supplierRepository.findById(
                                Long.valueOf(dto.getSupplierId()))
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplier not found"));

        User user =
                userRepository.findById(
                                Long.valueOf(dto.getCreatedBy()))
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        purchase.setSupplier(supplier);
        purchase.setCreatedBy(user);
        purchase.setInvoiceNumber(dto.getInvoiceNumber());
        purchase.setTotalAmount(dto.getTotalAmount());
        purchase.setTax(dto.getTax());
        purchase.setPaymentStatus(dto.getPaymentStatus());

        Purchase updated =
                purchaseRepository.save(purchase);

        return mapToDto(updated);
    }

    @Override
    public void deletePurchase(Long id) {

        purchaseRepository.deleteById(id);
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
