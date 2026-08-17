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

    private final PurchaseReturnService service;

    @PostMapping
    public ResponseEntity<PurchaseReturnResponseDTO> createPurchaseReturn(
            @Valid @RequestBody PurchaseReturnRequestDTO request) {

        return ResponseEntity.ok(
                service.createPurchaseReturn(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseReturnResponseDTO> getPurchaseReturnById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                service.getPurchaseReturnById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<PurchaseReturnResponseDTO>> getAllPurchaseReturns() {

        return ResponseEntity.ok(
                service.getAllPurchaseReturns()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseReturnResponseDTO> updatePurchaseReturn(
           @Valid @PathVariable String id,
            @RequestBody PurchaseReturnRequestDTO requestDTO) {

        return ResponseEntity.ok(
                service.updatePurchaseReturn(id, requestDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePurchaseReturn(
            @PathVariable String id) {

        service.deletePurchaseReturn(id);

        return ResponseEntity.ok(
                "Purchase Return Deleted Successfully"
        );
    }
}