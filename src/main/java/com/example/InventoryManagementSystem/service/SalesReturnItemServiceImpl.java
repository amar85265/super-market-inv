package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SalesReturnItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesReturnItemResponseDTO;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.model.SalesReturnItem;
import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.Repository.SalesReturnItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesReturnItemServiceImpl implements SalesReturnItemService {

    private final SalesReturnItemRepository salesReturnItemRepository;
    private final ProductRepository productRepository;

    // CREATE
    @Override
    public SalesReturnItemResponseDTO createItem(SalesReturnItemRequestDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // FIX: prevent null price crash
        BigDecimal price = product.getSellingPrice();
        if (price == null) {
            throw new RuntimeException("Product selling price is NULL");
        }

        BigDecimal total = price.multiply(BigDecimal.valueOf(dto.getQuantity()));

        SalesReturnItem item = new SalesReturnItem();
        item.setSalesReturnId(dto.getSalesReturnId());
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setPrice(price);
        item.setTotal(total);

        return mapToDTO(salesReturnItemRepository.save(item));
    }

    // GET ALL
    @Override
    public List<SalesReturnItemResponseDTO> getAll() {
        return salesReturnItemRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY RETURN ID
    @Override
    public List<SalesReturnItemResponseDTO> getByReturnId(Long salesReturnId) {
        return salesReturnItemRepository.findBySalesReturnId(salesReturnId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public SalesReturnItemResponseDTO updateItem(Long id, SalesReturnItemRequestDTO dto) {

        SalesReturnItem item = salesReturnItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        BigDecimal price = product.getSellingPrice();
        if (price == null) {
            throw new RuntimeException("Product selling price is NULL");
        }

        item.setSalesReturnId(dto.getSalesReturnId());
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setPrice(price);
        item.setTotal(price.multiply(BigDecimal.valueOf(dto.getQuantity())));

        return mapToDTO(salesReturnItemRepository.save(item));
    }

    // DELETE
    @Override
    public void deleteItem(Long id) {
        SalesReturnItem item = salesReturnItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        salesReturnItemRepository.delete(item);
    }

    // MAPPER
    private SalesReturnItemResponseDTO mapToDTO(SalesReturnItem item) {

        SalesReturnItemResponseDTO dto = new SalesReturnItemResponseDTO();

        dto.setSalesReturnItemId(item.getSalesReturnItemId());
        dto.setSalesReturnId(item.getSalesReturnId());
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setTotal(item.getTotal());

        return dto;
    }
}