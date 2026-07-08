package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.InvoiceItemRequestDto;
import com.example.InventoryManagementSystem.dto.InvoiceItemResponseDto;
import com.example.InventoryManagementSystem.service.InvoiceItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice-items")
@RequiredArgsConstructor
public class InvoiceItemController {

    private final InvoiceItemService service;

    // CREATE
    @PostMapping
    public InvoiceItemResponseDto createInvoiceItem(
            @Valid @RequestBody InvoiceItemRequestDto dto) {

        return service.createInvoiceItem(dto);
    }

    // READ ALL
    @GetMapping
    public List<InvoiceItemResponseDto> getAllInvoiceItems() {

        return service.getAllInvoiceItems();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public InvoiceItemResponseDto getInvoiceItemById(
            @PathVariable Long id) {

        return service.getInvoiceItemById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public InvoiceItemResponseDto updateInvoiceItem(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceItemRequestDto dto) {

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