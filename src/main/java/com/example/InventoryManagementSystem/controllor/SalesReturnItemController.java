package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.SalesReturnItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesReturnItemResponseDTO;
import com.example.InventoryManagementSystem.service.SalesReturnItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-return-items")
@RequiredArgsConstructor
public class SalesReturnItemController {

    private final SalesReturnItemService salesReturnItemService;

    // CREATE ITEM
    @PostMapping
    public ResponseEntity<SalesReturnItemResponseDTO> create(@RequestBody SalesReturnItemRequestDTO dto) {
        return ResponseEntity.ok(salesReturnItemService.createItem(dto));
    }

    // GET BY RETURN ID
    @GetMapping("/{salesReturnId}")
    public ResponseEntity<List<SalesReturnItemResponseDTO>> getByReturnId(@PathVariable Long salesReturnId) {
        return ResponseEntity.ok(salesReturnItemService.getByReturnId(salesReturnId));
    }

    // DELETE ITEM
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        salesReturnItemService.deleteItem(id);
        return ResponseEntity.ok("Sales return item deleted and stock updated");
    }
}