package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.Dto.PurchaseItemRequestDto;
import com.example.InventoryManagementSystem.Dto.PurchaseItemResponseDto;
import com.example.InventoryManagementSystem.service.PurchaseItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-items")
@RequiredArgsConstructor
public class PurchaseItemController {

    private final PurchaseItemService service;

    @PostMapping
    public PurchaseItemResponseDto createPurchaseItem(
            @RequestBody PurchaseItemRequestDto dto) {

        return service.createPurchaseItem(dto);
    }

    @GetMapping
    public List<PurchaseItemResponseDto> getAllPurchaseItems() {

        return service.getAllPurchaseItems();
    }

    @GetMapping("/{id}")
    public PurchaseItemResponseDto getPurchaseItemById(
            @PathVariable Long id) {

        return service.getPurchaseItemById(id);
    }

    @PutMapping("/{id}")
    public PurchaseItemResponseDto updatePurchaseItem(
            @PathVariable Long id,
            @RequestBody PurchaseItemRequestDto dto) {

        return service.updatePurchaseItem(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deletePurchaseItem(
            @PathVariable Long id) {

        service.deletePurchaseItem(id);

        return "Purchase Item deleted successfully";
    }
}