package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.PurchaseReturnRequestDTO;
import com.example.InventoryManagementSystem.dto.PurchaseReturnResponseDTO;
import com.example.InventoryManagementSystem.service.PurchaseReturnService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-returns")
@RequiredArgsConstructor
public class PurchaseReturnController {

    private final PurchaseReturnService purchaseReturnService;


    // CREATE PURCHASE RETURN
    @PostMapping
    public ResponseEntity<PurchaseReturnResponseDTO>
    createPurchaseReturn(
            @Valid @RequestBody PurchaseReturnRequestDTO requestDTO) {

        PurchaseReturnResponseDTO response =
                purchaseReturnService.createPurchaseReturn(
                        requestDTO
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // GET ALL PURCHASE RETURNS
    @GetMapping
    public ResponseEntity<List<PurchaseReturnResponseDTO>>
    getAllPurchaseReturns() {

        return ResponseEntity.ok(
                purchaseReturnService.getAllPurchaseReturns()
        );
    }


    // GET PURCHASE RETURN BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseReturnResponseDTO>
    getPurchaseReturnById(
            @PathVariable Integer id) {

        PurchaseReturnResponseDTO response =
                purchaseReturnService.getPurchaseReturnById(id);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }


    // UPDATE PURCHASE RETURN
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseReturnResponseDTO>
    updatePurchaseReturn(
            @PathVariable Integer id,
            @Valid @RequestBody
            PurchaseReturnRequestDTO requestDTO) {

        PurchaseReturnResponseDTO response =
                purchaseReturnService.updatePurchaseReturn(
                        id,
                        requestDTO
                );

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }


    // DELETE PURCHASE RETURN
    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deletePurchaseReturn(
            @PathVariable Integer id) {

        PurchaseReturnResponseDTO existing =
                purchaseReturnService
                        .getPurchaseReturnById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        purchaseReturnService.deletePurchaseReturn(id);

        return ResponseEntity.ok(
                "Purchase return deleted successfully"
        );
    }
}