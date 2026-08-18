package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.InvoiceRequestDTO;
import com.example.InventoryManagementSystem.dto.InvoiceResponseDTO;

import java.util.List;
import java.util.UUID;

public interface InvoiceService {

    InvoiceResponseDTO createInvoice(InvoiceRequestDTO request);

<<<<<<< Updated upstream
    InvoiceResponseDto getInvoiceById(Long invoiceId);

    List<InvoiceResponseDto> getAllInvoices();

    InvoiceResponseDto updateInvoice(
            Long invoiceId,
            InvoiceRequestDto dto);

    void deleteInvoice(Long invoiceId);
}
=======
    InvoiceResponseDTO getInvoice(String invoiceId);

    List<InvoiceResponseDTO> getAllInvoices();

    void deleteInvoice(String invoiceId);
}
>>>>>>> Stashed changes
