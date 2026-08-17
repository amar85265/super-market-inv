package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.InvoiceItemRequestDto;
import com.example.InventoryManagementSystem.dto.InvoiceItemResponseDto;

import java.util.List;
public interface InvoiceItemService {

    InvoiceItemResponseDto createInvoiceItem(InvoiceItemRequestDto dto);

    List<InvoiceItemResponseDto> getAllInvoiceItems();

    InvoiceItemResponseDto getInvoiceItemById(String id);

    InvoiceItemResponseDto updateInvoiceItem(String id, InvoiceItemRequestDto dto);

    void deleteInvoiceItem(String id);
}