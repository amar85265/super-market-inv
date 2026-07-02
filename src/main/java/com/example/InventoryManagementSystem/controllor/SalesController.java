package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.SalesRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesResponseDTO;
import com.example.InventoryManagementSystem.service.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @PostMapping
    public ResponseEntity<SalesResponseDTO> create(
            @Valid @RequestBody SalesRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(salesService.createSale(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesResponseDTO> getById(
            @PathVariable String id) {
        return ResponseEntity.ok(salesService.getSaleById(id));
    }

    @GetMapping
    public ResponseEntity<List<SalesResponseDTO>> getAll() {
        return ResponseEntity.ok(salesService.getAllSales());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesResponseDTO> update(
            @PathVariable String id,
            @Valid @RequestBody SalesRequestDTO dto) {
        return ResponseEntity.ok(salesService.updateSale(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        salesService.deleteSale(id);
        return ResponseEntity.ok("Sale deleted successfully");
    }
}