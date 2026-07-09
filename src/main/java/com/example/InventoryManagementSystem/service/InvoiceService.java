package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.InvoiceRequestDto;
import com.example.InventoryManagementSystem.dto.InvoiceResponseDto;

import java.util.List;

public interface InvoiceService {

    InvoiceResponseDto createInvoice(InvoiceRequestDto dto);

    InvoiceResponseDto getInvoiceById(Long invoiceId);

    List<InvoiceResponseDto> getAllInvoices();

    InvoiceResponseDto updateInvoice(
            Long invoiceId,
            InvoiceRequestDto dto);

    void deleteInvoice(Long invoiceId);
}