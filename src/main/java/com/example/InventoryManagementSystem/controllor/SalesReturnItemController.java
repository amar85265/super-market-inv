package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.SalesReturnItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesReturnItemResponseDTO;
import com.example.InventoryManagementSystem.service.SalesReturnItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-return-items")
@RequiredArgsConstructor
public class SalesReturnItemController {

    private final SalesReturnItemService salesReturnItemService;

    @PostMapping
    public ResponseEntity<SalesReturnItemResponseDTO> create(
            @Valid @RequestBody SalesReturnItemRequestDTO dto) {
        return ResponseEntity.ok(salesReturnItemService.createItem(dto));
    }

    @GetMapping
    public ResponseEntity<List<SalesReturnItemResponseDTO>> getAll() {
        return ResponseEntity.ok(salesReturnItemService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesReturnItemResponseDTO> getById(
            @PathVariable String id) {
        return ResponseEntity.ok(salesReturnItemService.getById(id));
    }

    @GetMapping("/return/{salesReturnId}")
    public ResponseEntity<List<SalesReturnItemResponseDTO>> getByReturnId(
            @PathVariable String salesReturnId) {
        return ResponseEntity.ok(salesReturnItemService.getByReturnId(salesReturnId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesReturnItemResponseDTO> update(
            @PathVariable String id,
            @Valid @RequestBody SalesReturnItemRequestDTO dto) {
        return ResponseEntity.ok(salesReturnItemService.updateItem(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        salesReturnItemService.deleteItem(id);
        return ResponseEntity.ok("Sales return item deleted and stock updated");
    }
}