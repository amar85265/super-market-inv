package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.PurchaseRequestDto;
import com.example.InventoryManagementSystem.dto.PurchaseResponseDto;
import com.example.InventoryManagementSystem.service.PurchaseService;
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
            @RequestBody PurchaseRequestDto request) {

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
            @PathVariable String id) {

        return ResponseEntity.ok(
                purchaseService.getPurchaseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseResponseDto>
    updatePurchase(
            @PathVariable String id,
            @RequestBody PurchaseRequestDto request) {

        return ResponseEntity.ok(
                purchaseService.updatePurchase(
                        id,
                        request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deletePurchase(
            @PathVariable String id) {

        purchaseService.deletePurchase(id);

        return ResponseEntity.ok(
                "Purchase Deleted Successfully");
    }


}