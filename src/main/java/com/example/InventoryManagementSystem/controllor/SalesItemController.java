package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.SalesItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesItemResponseDTO;
import com.example.InventoryManagementSystem.service.SalesItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-items")
@RequiredArgsConstructor
public class SalesItemController {

    private final SalesItemService salesItemService;

    // CREATE SALES ITEM
    @PostMapping
    public ResponseEntity<SalesItemResponseDTO> create(@RequestBody SalesItemRequestDTO dto) {
        return ResponseEntity.ok(salesItemService.createSalesItem(dto));
    }

    // GET BY SALE ID
    @GetMapping("/sale/{saleId}")
    public ResponseEntity<List<SalesItemResponseDTO>> getBySaleId(@PathVariable Long saleId) {
        return ResponseEntity.ok(salesItemService.getItemsBySaleId(saleId));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        salesItemService.deleteSalesItem(id);
        return ResponseEntity.ok("Sales item deleted and stock restored");
    }
}