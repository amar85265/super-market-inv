package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.InvoiceDto;

import java.util.List;

public interface InvoiceService {

    InvoiceDto createInvoice(InvoiceDto dto);

    List<InvoiceDto> getAllInvoices();

    InvoiceDto getInvoiceById(Long id);

    InvoiceDto updateInvoice(Long id, InvoiceDto dto);

    void deleteInvoice(Long id);
}