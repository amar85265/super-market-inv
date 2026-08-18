package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.InvoiceRequestDTO;
import com.example.InventoryManagementSystem.dto.InvoiceResponseDTO;
import com.example.InventoryManagementSystem.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> createInvoice(
            @Valid @RequestBody InvoiceRequestDTO request) {

        InvoiceResponseDTO response =
                invoiceService.createInvoice(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

<<<<<<< Updated upstream
    @GetMapping("/{id}")
    public InvoiceResponseDto getInvoiceById(
            @PathVariable Long id) {
=======
    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponseDTO> getInvoice(
            @PathVariable String invoiceId) {
>>>>>>> Stashed changes

        return ResponseEntity.ok(
                invoiceService.getInvoice(invoiceId)
        );
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices() {

        return ResponseEntity.ok(
                invoiceService.getAllInvoices()
        );
    }

<<<<<<< Updated upstream
    @PutMapping("/{id}")
    public InvoiceResponseDto updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceRequestDto dto) {
=======
    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> deleteInvoice(
            @PathVariable String invoiceId) {
>>>>>>> Stashed changes

        invoiceService.deleteInvoice(invoiceId);

<<<<<<< Updated upstream
    @DeleteMapping("/{id}")
    public String deleteInvoice(
            @PathVariable Long id) {

        service.deleteInvoice(id);

        return "Invoice deleted successfully";
=======
        return ResponseEntity.noContent().build();
>>>>>>> Stashed changes
    }
}