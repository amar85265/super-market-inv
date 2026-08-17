package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.*;

import java.util.List;

public interface PaymentTransactionService {

    PaymentTransactionResponseDTO create(PaymentTransactionRequestDTO dto);

    List<PaymentTransactionResponseDTO> getAll();

    PaymentTransactionResponseDTO getById(String id);

    PaymentTransactionResponseDTO update(String id, PaymentTransactionRequestDTO dto);

    void delete(String id);
}