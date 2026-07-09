package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.InvoiceRequestDto;
import com.example.InventoryManagementSystem.dto.InvoiceResponseDto;
import com.example.InventoryManagementSystem.service.InvoiceService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService service;

    @PostMapping
    public InvoiceResponseDto createInvoice(
            @Valid @RequestBody InvoiceRequestDto dto) {

        return service.createInvoice(dto);
    }

    @GetMapping("/{id}")
    public InvoiceResponseDto getInvoiceById(
            @PathVariable Long id) {

        return service.getInvoiceById(id);
    }

    @GetMapping
    public List<InvoiceResponseDto> getAllInvoices() {

        return service.getAllInvoices();
    }

    @PutMapping("/{id}")
    public InvoiceResponseDto updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceRequestDto dto) {

        return service.updateInvoice(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteInvoice(
            @PathVariable Long id) {

        service.deleteInvoice(id);

        return "Invoice deleted successfully";
    }
}