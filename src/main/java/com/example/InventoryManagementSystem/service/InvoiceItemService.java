package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.InvoiceItemDto;

import java.util.List;

public interface InvoiceItemService {

    InvoiceItemDto createInvoiceItem(InvoiceItemDto dto);

    List<InvoiceItemDto> getAllInvoiceItems();

    InvoiceItemDto getInvoiceItemById(Long id);

    InvoiceItemDto updateInvoiceItem(Long id,
                                     InvoiceItemDto dto);

    void deleteInvoiceItem(Long id);
}