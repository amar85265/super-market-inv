package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.PurchaseRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseResponseDto;
import com.example.InventoryManagementSystem.model.Purchase;
import com.example.InventoryManagementSystem.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {


    private final PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<PurchaseResponseDto> createPurchase(
            @Valid @RequestBody PurchaseRequestDto request) {

        return ResponseEntity.ok(
                purchaseService.createPurchase(request));

    }

    @GetMapping
    public ResponseEntity<List<PurchaseResponseDto>>
    getAllPurchases() {

        return ResponseEntity.ok(
                purchaseService.getAllPurchases());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDto>
    getPurchaseById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                purchaseService.getPurchaseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseResponseDto>
    updatePurchase(
           @Valid @PathVariable Long id,
            @RequestBody PurchaseRequestDto request) {

        return ResponseEntity.ok(
                purchaseService.updatePurchase(
                        id,
                        request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deletePurchase(
            @PathVariable Long id) {

        purchaseService.deletePurchase(id);

        return ResponseEntity.ok(
                "Purchase Deleted Successfully");
    }


}
