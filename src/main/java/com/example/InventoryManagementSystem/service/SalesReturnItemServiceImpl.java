package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SalesReturnItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesReturnItemResponseDTO;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.model.SalesReturn;
import com.example.InventoryManagementSystem.model.SalesReturnItem;
import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.Repository.SalesReturnItemRepository;
import com.example.InventoryManagementSystem.Repository.SalesReturnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesReturnItemServiceImpl
        implements SalesReturnItemService {

    private final SalesReturnItemRepository salesReturnItemRepository;

    private final ProductRepository productRepository;

    private final SalesReturnRepository salesReturnRepository;

    // CREATE RETURN ITEM
    @Override
    public SalesReturnItemResponseDTO createItem(
            SalesReturnItemRequestDTO dto) {

        // CHECK SALES RETURN
        SalesReturn salesReturn =
                salesReturnRepository.findById(
                                dto.getSalesReturnId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sales Return not found"));

        // CHECK PRODUCT
        Product product =
                productRepository.findById(
                                dto.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        // STOCK INCREASE
        product.setStockQuantity(
                product.getStockQuantity()
                        + dto.getQuantity());

        productRepository.save(product);

        // CALCULATE TOTAL
        BigDecimal total = dto.getPrice()
                .multiply(
                        BigDecimal.valueOf(
                                dto.getQuantity()));

        // CREATE ENTITY
        SalesReturnItem item = new SalesReturnItem();

        item.setSalesReturnId(
                dto.getSalesReturnId());

        item.setProductId(
                dto.getProductId());

        item.setQuantity(
                dto.getQuantity());

        item.setPrice(
                dto.getPrice());

        item.setTotal(total);

        // SAVE ITEM
        SalesReturnItem savedItem =
                salesReturnItemRepository.save(item);

        // UPDATE SALES RETURN TOTAL
        BigDecimal currentTotal =
                salesReturn.getTotalAmount() == null
                        ? BigDecimal.ZERO
                        : salesReturn.getTotalAmount();

        salesReturn.setTotalAmount(
                currentTotal.add(total));

        salesReturnRepository.save(salesReturn);

        return mapToDTO(savedItem);
    }

    // GET ITEMS BY RETURN ID
    @Override
    public List<SalesReturnItemResponseDTO>
    getByReturnId(Long salesReturnId) {

        return salesReturnItemRepository
                .findBySalesReturnId(salesReturnId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // DELETE ITEM
    @Override
    public void deleteItem(Long id) {

        SalesReturnItem item =
                salesReturnItemRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Item not found"));

        Product product =
                productRepository.findById(
                                item.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        // ROLLBACK STOCK
        product.setStockQuantity(
                product.getStockQuantity()
                        - item.getQuantity());

        productRepository.save(product);

        // UPDATE RETURN TOTAL
        SalesReturn salesReturn =
                salesReturnRepository.findById(
                                item.getSalesReturnId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sales Return not found"));

        BigDecimal currentTotal =
                salesReturn.getTotalAmount() == null
                        ? BigDecimal.ZERO
                        : salesReturn.getTotalAmount();

        salesReturn.setTotalAmount(
                currentTotal.subtract(item.getTotal()));

        salesReturnRepository.save(salesReturn);

        // DELETE ITEM
        salesReturnItemRepository.delete(item);
    }

    // DTO MAPPER
    private SalesReturnItemResponseDTO mapToDTO(
            SalesReturnItem item) {

        SalesReturnItemResponseDTO dto =
                new SalesReturnItemResponseDTO();

        dto.setSalesReturnItemId(
                item.getSalesReturnItemId());

        dto.setSalesReturnId(
                item.getSalesReturnId());

        dto.setProductId(
                item.getProductId());

        dto.setQuantity(
                item.getQuantity());

        dto.setPrice(
                item.getPrice());

        dto.setTotal(
                item.getTotal());

        return dto;
    }
}