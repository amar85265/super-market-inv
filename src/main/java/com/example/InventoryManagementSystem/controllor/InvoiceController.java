package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.InvoiceDto;
import com.example.InventoryManagementSystem.service.InvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService service;

    // CREATE
    @PostMapping
    public InvoiceDto createInvoice(@RequestBody InvoiceDto dto) {

        return service.createInvoice(dto);
    }

    // READ ALL
    @GetMapping
    public List<InvoiceDto> getAllInvoices() {

        return service.getAllInvoices();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public InvoiceDto getInvoiceById(@PathVariable Long id) {

        return service.getInvoiceById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public InvoiceDto updateInvoice(@PathVariable Long id,
                                    @RequestBody InvoiceDto dto) {

        return service.updateInvoice(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteInvoice(@PathVariable Long id) {

        service.deleteInvoice(id);

        return "Invoice deleted successfully";
    }
}