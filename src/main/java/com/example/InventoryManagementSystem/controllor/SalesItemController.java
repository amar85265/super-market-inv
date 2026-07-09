package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.SalesItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesItemResponseDTO;
import com.example.InventoryManagementSystem.service.SalesItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-items")
@RequiredArgsConstructor
public class SalesItemController {

    private final SalesItemService salesItemService;

    @PostMapping
    public ResponseEntity<SalesItemResponseDTO> create(@Valid @RequestBody SalesItemRequestDTO dto) {
        SalesItemResponseDTO created = salesItemService.createSalesItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<SalesItemResponseDTO>> getAll() {
        return ResponseEntity.ok(salesItemService.getAllSalesItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesItemResponseDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(salesItemService.getSalesItemById(id));
    }

    @GetMapping("/sale/{saleId}")
    public ResponseEntity<List<SalesItemResponseDTO>> getBySaleId(@PathVariable String saleId) {
        return ResponseEntity.ok(salesItemService.getItemsBySaleId(saleId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesItemResponseDTO> update(
            @PathVariable String id,
            @Valid @RequestBody SalesItemRequestDTO dto) {
        return ResponseEntity.ok(salesItemService.updateSalesItem(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        salesItemService.deleteSalesItem(id);
        return ResponseEntity.noContent().build();
    }
}