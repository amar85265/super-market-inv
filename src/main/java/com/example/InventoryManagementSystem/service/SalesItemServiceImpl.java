package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SalesItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesItemResponseDTO;
import com.example.InventoryManagementSystem.model.SalesItem;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.Repository.SalesItemRepository;
import com.example.InventoryManagementSystem.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesItemServiceImpl implements SalesItemService {

    private final SalesItemRepository salesItemRepository;
    private final ProductRepository productRepository;

    // CREATE SALES ITEM
    @Override
    public SalesItemResponseDTO createSalesItem(SalesItemRequestDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 🔥 STOCK CHECK
        if (product.getStockQuantity() < dto.getQuantity()) {
            throw new RuntimeException("Not enough stock available");
        }

        // 🔥 DECREASE STOCK (REAL INVENTORY LOGIC)
        product.setStockQuantity(product.getStockQuantity() - dto.getQuantity());
        productRepository.save(product);

        SalesItem item = new SalesItem();

        item.setSaleId(dto.getSaleId());
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setSellingPrice(dto.getSellingPrice());

        // 🔥 TOTAL CALCULATION
        BigDecimal total = dto.getSellingPrice()
                .multiply(BigDecimal.valueOf(dto.getQuantity()));

        item.setTotal(total);

        SalesItem saved = salesItemRepository.save(item);

        return mapToDTO(saved);
    }

    // GET BY SALE ID
    @Override
    public List<SalesItemResponseDTO> getItemsBySaleId(Long saleId) {

        return salesItemRepository.findBySaleId(saleId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // DELETE ITEM
    @Override
    public void deleteSalesItem(Long id) {

        SalesItem item = salesItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales item not found"));

        // 🔥 RESTORE STOCK WHEN DELETING
        Product product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
        productRepository.save(product);

        salesItemRepository.delete(item);
    }

    // MAPPER
    private SalesItemResponseDTO mapToDTO(SalesItem item) {

        SalesItemResponseDTO dto = new SalesItemResponseDTO();

        dto.setSaleItemId(item.getSaleItemId());
        dto.setSaleId(item.getSaleId());
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setSellingPrice(item.getSellingPrice());
        dto.setTotal(item.getTotal());

        return dto;
    }
}