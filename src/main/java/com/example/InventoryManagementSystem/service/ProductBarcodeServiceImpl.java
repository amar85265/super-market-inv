package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.ProductBarcodeRepository;
import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.dto.ProductBarcodeRequestDTO;
import com.example.InventoryManagementSystem.dto.ProductBarcodeResponseDTO;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.model.ProductBarcode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductBarcodeServiceImpl implements ProductBarcodeService {

    private final ProductBarcodeRepository repository;
    private final ProductRepository productRepository;

    @Override
    public ProductBarcodeResponseDTO createBarcode(ProductBarcodeRequestDTO request) {

        // Check Product Exists
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found with ID : " + request.getProductId()));

        // Check Barcode Already Exists
        if (repository.findByBarcode(request.getBarcode()).isPresent()) {
            throw new RuntimeException("Barcode already exists");
        }

        ProductBarcode barcode = ProductBarcode.builder()
                .productId(product.getProductId())
                .barcode(request.getBarcode())
                .build();

        repository.save(barcode);

        return mapToDTO(barcode);
    }

    @Override
    public List<ProductBarcodeResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductBarcodeResponseDTO updateBarcode(Long id, ProductBarcodeRequestDTO request) {

        ProductBarcode barcode = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Barcode not found with ID : " + id));

        // Check Product Exists
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found with ID : " + request.getProductId()));

        // Check Duplicate Barcode
        repository.findByBarcode(request.getBarcode())
                .ifPresent(existing -> {
                    if (!existing.getBarcodeId().equals(id)) {
                        throw new RuntimeException("Barcode already exists");
                    }
                });

        barcode.setProductId(product.getProductId());
        barcode.setBarcode(request.getBarcode());

        repository.save(barcode);

        return mapToDTO(barcode);
    }

    @Override
    public List<ProductBarcodeResponseDTO> getByProductId(Long productId) {

        return repository.findByProductId(productId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductBarcodeResponseDTO getByBarcode(String barcode) {

        ProductBarcode entity = repository.findByBarcode(barcode)
                .orElseThrow(() ->
                        new RuntimeException("Barcode not found"));

        return mapToDTO(entity);
    }

    @Override
    public void deleteBarcode(Long barcodeId) {

        ProductBarcode barcode = repository.findById(barcodeId)
                .orElseThrow(() ->
                        new RuntimeException("Barcode not found with ID : " + barcodeId));

        repository.delete(barcode);
    }

    private ProductBarcodeResponseDTO mapToDTO(ProductBarcode entity) {

        ProductBarcodeResponseDTO dto = new ProductBarcodeResponseDTO();

        dto.setBarcodeId(entity.getBarcodeId());
        dto.setProductId(entity.getProductId());
        dto.setBarcode(entity.getBarcode());

        return dto;
    }
}