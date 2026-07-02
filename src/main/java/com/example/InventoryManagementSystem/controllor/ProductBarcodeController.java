package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.ProductBarcodeRequestDTO;
import com.example.InventoryManagementSystem.dto.ProductBarcodeResponseDTO;
import com.example.InventoryManagementSystem.service.ProductBarcodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-barcodes")
@RequiredArgsConstructor
public class ProductBarcodeController {

    private final ProductBarcodeService service;

    // CREATE BARCODE
    @PostMapping
    public ProductBarcodeResponseDTO create(@Valid @RequestBody ProductBarcodeRequestDTO request) {
        return service.createBarcode(request);
    }

    // GET ALL BARCODES
    @GetMapping
    public List<ProductBarcodeResponseDTO> getAll() {
        return service.getAll();
    }

    // GET BARCODES BY PRODUCT ID
    @GetMapping("/product/{productId}")
    public List<ProductBarcodeResponseDTO> getByID(@PathVariable Long productId) {
        return service.getByProductId(productId);
    }

    // SCAN BARCODE
    @GetMapping("/scan/{barcode}")
    public ProductBarcodeResponseDTO scan(@PathVariable String barcode) {
        return service.getByBarcode(barcode);
    }

    // UPDATE BARCODE
    @PutMapping("/{id}")
    public ProductBarcodeResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody ProductBarcodeRequestDTO request) {
        return service.updateBarcode(id, request);
    }

    // DELETE BARCODE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteBarcode(id);
        return "Deleted Successfully";
    }
}