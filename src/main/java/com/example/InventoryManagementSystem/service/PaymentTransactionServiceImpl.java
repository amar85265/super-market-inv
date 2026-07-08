package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.InvoiceRepository;
import com.example.InventoryManagementSystem.dto.PaymentTransactionRequestDTO;
import com.example.InventoryManagementSystem.dto.PaymentTransactionResponseDTO;
import com.example.InventoryManagementSystem.model.PaymentTransaction;
import com.example.InventoryManagementSystem.Repository.PaymentTransactionRepository;
import com.example.InventoryManagementSystem.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PaymentTransactionRepository repository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public PaymentTransactionResponseDTO create(PaymentTransactionRequestDTO dto) {

        if (!invoiceRepository.existsById(dto.getInvoiceId().longValue())) {
            throw new RuntimeException("Invoice ID not found.");
        }


        PaymentTransaction payment = new PaymentTransaction();

        payment.setInvoiceId(dto.getInvoiceId());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setTransactionReference(dto.getTransactionReference());
        payment.setAmount(dto.getAmount());

        PaymentTransaction saved = repository.save(payment);

        return mapToResponse(saved);
    }

    @Override
    public List<PaymentTransactionResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentTransactionResponseDTO getById(Long id) {

        PaymentTransaction payment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return mapToResponse(payment);
    }

    @Override
    public PaymentTransactionResponseDTO update(Long id,
                                                PaymentTransactionRequestDTO dto) {

        if (!invoiceRepository.existsById(dto.getInvoiceId().longValue())) {
            throw new RuntimeException("Invoice ID not found.");
        }


        PaymentTransaction payment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setInvoiceId(dto.getInvoiceId());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setTransactionReference(dto.getTransactionReference());
        payment.setAmount(dto.getAmount());

        PaymentTransaction updated = repository.save(payment);

        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private PaymentTransactionResponseDTO mapToResponse(
            PaymentTransaction payment) {

        return PaymentTransactionResponseDTO.builder()
                .transactionId(payment.getTransactionId())
                .invoiceId(payment.getInvoiceId())
                .paymentMethod(payment.getPaymentMethod())
                .transactionReference(payment.getTransactionReference())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .build();
    }
}