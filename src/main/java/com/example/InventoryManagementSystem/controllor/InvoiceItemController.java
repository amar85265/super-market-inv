package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.InvoiceItemDto;
import com.example.InventoryManagementSystem.service.InvoiceItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice-items")
public class InvoiceItemController {

    @Autowired
    private InvoiceItemService service;

    // CREATE
    @PostMapping
    public InvoiceItemDto createInvoiceItem(
            @RequestBody InvoiceItemDto dto) {

        return service.createInvoiceItem(dto);
    }

    // READ ALL
    @GetMapping
    public List<InvoiceItemDto> getAllInvoiceItems() {

        return service.getAllInvoiceItems();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public InvoiceItemDto getInvoiceItemById(
            @PathVariable Long id) {

        return service.getInvoiceItemById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public InvoiceItemDto updateInvoiceItem(
            @PathVariable Long id,
            @RequestBody InvoiceItemDto dto) {

        return service.updateInvoiceItem(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteInvoiceItem(
            @PathVariable Long id) {

        service.deleteInvoiceItem(id);

        return "Invoice Item deleted successfully";
    }
}