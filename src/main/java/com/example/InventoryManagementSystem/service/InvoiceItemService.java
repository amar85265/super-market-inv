package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.model.InvoiceItem;

import java.util.List;

public interface InvoiceItemService {

    InvoiceItem createInvoiceItem(InvoiceItem invoiceItem);

    List<InvoiceItem> getAllInvoiceItems();

<<<<<<< Updated upstream
    InvoiceItemResponseDto getInvoiceItemById(Long id);

    InvoiceItemResponseDto updateInvoiceItem(Long id, InvoiceItemRequestDto dto);
=======
    InvoiceItem getInvoiceItemById(String id);

    InvoiceItem updateInvoiceItem(String id, InvoiceItem invoiceItem);
>>>>>>> Stashed changes

    void deleteInvoiceItem(Long id);
}