package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.ApiResponse;
import com.example.InventoryManagementSystem.dto.ApiResponse;
import com.example.InventoryManagementSystem.dto.ProductRequestDTO;
import com.example.InventoryManagementSystem.dto.ProductResponseDTO;
import com.example.InventoryManagementSystem.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // CREATE PRODUCT
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(
            @Valid @RequestBody ProductRequestDTO request) {

        ProductResponseDTO response =
                productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Product Created Successfully",
                        response));
    }

    // GET PRODUCT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getById(
            @PathVariable String id) {

        ProductResponseDTO response =
                productService.getProductById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Product Retrieved Successfully",
                        response));
    }

    // GET ALL PRODUCTS
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAll() {

        List<ProductResponseDTO> response =
                productService.getAllProducts();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Products Retrieved Successfully",
                        response));
    }

    // UPDATE PRODUCT
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> update(
            @PathVariable String id,
            @Valid @RequestBody ProductRequestDTO request) {

        ProductResponseDTO response =
                productService.updateProduct(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Product Updated Successfully",
                        response));
    }

    // DELETE PRODUCT
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(
            @PathVariable String id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Product Deleted Successfully",
                        "Deleted"));
    }
}