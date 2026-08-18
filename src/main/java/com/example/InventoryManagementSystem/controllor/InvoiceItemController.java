package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.model.InvoiceItem;
import com.example.InventoryManagementSystem.service.InvoiceItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoice-items")
@RequiredArgsConstructor
public class InvoiceItemController {

    private final InvoiceItemService invoiceItemService;

    // CREATE Invoice Item
    @PostMapping
    public ResponseEntity<InvoiceItem> createInvoiceItem(
            @Valid @RequestBody InvoiceItem invoiceItem) {

        InvoiceItem createdItem =
                invoiceItemService.createInvoiceItem(invoiceItem);

        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    // GET all Invoice Items
    @GetMapping
    public ResponseEntity<List<InvoiceItem>> getAllInvoiceItems() {

        List<InvoiceItem> items =
                invoiceItemService.getAllInvoiceItems();

        return ResponseEntity.ok(items);
    }

    // GET Invoice Item by ID
    @GetMapping("/{id}")
<<<<<<< Updated upstream
    public InvoiceItemResponseDto getInvoiceItemById(
            @PathVariable Long id) {
=======
    public ResponseEntity<InvoiceItem> getInvoiceItemById(
            @PathVariable String id) {
>>>>>>> Stashed changes

        InvoiceItem item =
                invoiceItemService.getInvoiceItemById(id);

        return ResponseEntity.ok(item);
    }

    // UPDATE Invoice Item
    @PutMapping("/{id}")
<<<<<<< Updated upstream
    public InvoiceItemResponseDto updateInvoiceItem(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceItemRequestDto dto) {
=======
    public ResponseEntity<InvoiceItem> updateInvoiceItem(
            @PathVariable String id,
            @Valid @RequestBody InvoiceItem invoiceItem) {
>>>>>>> Stashed changes

        InvoiceItem updatedItem =
                invoiceItemService.updateInvoiceItem(id, invoiceItem);

        return ResponseEntity.ok(updatedItem);
    }

    // DELETE Invoice Item
    @DeleteMapping("/{id}")
<<<<<<< Updated upstream
    public String deleteInvoiceItem(
            @PathVariable Long id) {
=======
    public ResponseEntity<Void> deleteInvoiceItem(
            @PathVariable String id) {
>>>>>>> Stashed changes

        invoiceItemService.deleteInvoiceItem(id);

        return ResponseEntity.noContent().build();
    }
}