package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.InvoiceItemRequestDto;
import com.example.InventoryManagementSystem.dto.InvoiceItemResponseDto;

import java.util.List;
public interface InvoiceItemService {

    InvoiceItemResponseDto createInvoiceItem(InvoiceItemRequestDto dto);

    List<InvoiceItemResponseDto> getAllInvoiceItems();

    InvoiceItemResponseDto getInvoiceItemById(Long id);

    InvoiceItemResponseDto updateInvoiceItem(Long id, InvoiceItemRequestDto dto);

    void deleteInvoiceItem(Long id);
}