package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.*;
import com.example.InventoryManagementSystem.model.PaymentTransaction;
import com.example.InventoryManagementSystem.Repository.PaymentTransactionRepository;
import com.example.InventoryManagementSystem.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PaymentTransactionRepository repo;

    @Override
    public PaymentTransactionResponseDTO create(PaymentTransactionRequestDTO dto) {

        PaymentTransaction p = PaymentTransaction.builder()
                .invoiceId(dto.getInvoiceId())
                .paymentMethod(dto.getPaymentMethod())
                .transactionReference(dto.getTransactionReference())
                .amount(dto.getAmount())
                .build();

        repo.save(p);
        return map(p);
    }

    @Override
    public List<PaymentTransactionResponseDTO> getAll() {
        return repo.findAll().stream().map(this::map).toList();
    }

    @Override
    public PaymentTransactionResponseDTO getById(Long id) {
        PaymentTransaction p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return map(p);
    }

    @Override
    public PaymentTransactionResponseDTO update(Long id, PaymentTransactionRequestDTO dto) {

        PaymentTransaction p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        p.setInvoiceId(dto.getInvoiceId());
        p.setPaymentMethod(dto.getPaymentMethod());
        p.setTransactionReference(dto.getTransactionReference());
        p.setAmount(dto.getAmount());

        repo.save(p);

        return map(p);
    }

    @Override
    public void delete(Long id) {
        PaymentTransaction p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        repo.delete(p);
    }

    private PaymentTransactionResponseDTO map(PaymentTransaction p) {
        return PaymentTransactionResponseDTO.builder()
                .transactionId(p.getTransactionId())
                .invoiceId(p.getInvoiceId())
                .paymentMethod(p.getPaymentMethod())
                .transactionReference(p.getTransactionReference())
                .amount(p.getAmount())
                .paymentDate(p.getPaymentDate())
                .build();
    }
}