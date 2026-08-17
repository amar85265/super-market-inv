package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.PurchaseReturnItemRequestDTO;
import com.example.InventoryManagementSystem.dto.PurchaseReturnItemResponseDTO;
import com.example.InventoryManagementSystem.model.PurchaseReturnItem;
import com.example.InventoryManagementSystem.service.PurchaseReturnItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-return-items")
@RequiredArgsConstructor
public class PurchaseReturnItemController {

    private final PurchaseReturnItemService service;
    @PostMapping
    public ResponseEntity<PurchaseReturnItemResponseDTO> createPurchaseReturnItem(
            @Valid @RequestBody PurchaseReturnItemRequestDTO requestDTO) {

        PurchaseReturnItemResponseDTO responseDTO =
                service.createPurchaseReturnItem(requestDTO);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseReturnItemResponseDTO>
    getPurchaseReturnItemById(@PathVariable String id) {

        return ResponseEntity.ok(
                service.getPurchaseReturnItemById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<PurchaseReturnItemResponseDTO>>
    getAllPurchaseReturnItems() {

        return ResponseEntity.ok(
                service.getAllPurchaseReturnItems()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseReturnItemResponseDTO>
    updatePurchaseReturnItem(
            @PathVariable String id,
            @Valid @RequestBody PurchaseReturnItemRequestDTO requestDTO) {

        return ResponseEntity.ok(
                service.updatePurchaseReturnItem(id, requestDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePurchaseReturnItem(
            @PathVariable String id) {

        service.deletePurchaseReturnItem(id);

        return ResponseEntity.ok(
                "Purchase Return Item Deleted Successfully"
        );
    }
}