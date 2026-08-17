package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.InvoiceRequestDto;
import com.example.InventoryManagementSystem.dto.InvoiceResponseDto;

import java.util.List;

public interface InvoiceService {

    InvoiceResponseDto createInvoice(InvoiceRequestDto dto);

    InvoiceResponseDto getInvoiceById(String invoiceId);

    List<InvoiceResponseDto> getAllInvoices();

    InvoiceResponseDto updateInvoice(
            String invoiceId,
            InvoiceRequestDto dto);

    void deleteInvoice(String invoiceId);
}