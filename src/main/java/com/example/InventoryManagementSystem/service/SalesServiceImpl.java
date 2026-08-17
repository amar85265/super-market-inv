package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SalesItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesItemResponseDTO;
import com.example.InventoryManagementSystem.dto.SalesRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesResponseDTO;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.model.Sales;
import com.example.InventoryManagementSystem.model.SalesItem;
import com.example.InventoryManagementSystem.Repository.CustomerRepository;
import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.Repository.SalesItemRepository;
import com.example.InventoryManagementSystem.Repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SalesRepository salesRepository;
    private final CustomerRepository customerRepository;
    private final SalesItemRepository salesItemRepository;
    private final ProductRepository productRepository;

    // CREATE SALE
    @Override
    public SalesResponseDTO createSale(SalesRequestDTO dto) {

        Sales sale = new Sales();

        sale.setCustomerId(dto.getCustomerId());
        sale.setCreatedBy(dto.getCreatedBy());
        sale.setInvoiceNumber(dto.getInvoiceNumber());
        sale.setPaymentStatus(dto.getPaymentStatus());
        sale.setTotalAmount(dto.getTotalAmount());
        sale.setSaleDate(LocalDateTime.now());

        Sales saved = salesRepository.save(sale);

        saveSalesItems(saved, dto.getItems());

        return mapToDTO(saved);
    }

    // GET BY ID
    @Override
    public SalesResponseDTO getSaleById(Long id) {

        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));

        return mapToDTO(sale);
    }

    // GET ALL
    @Override
    public List<SalesResponseDTO> getAllSales() {

        return salesRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public SalesResponseDTO updateSale(Long id, SalesRequestDTO dto) {

        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));

        sale.setCustomerId(dto.getCustomerId());
        sale.setCreatedBy(dto.getCreatedBy());
        sale.setInvoiceNumber(dto.getInvoiceNumber());
        sale.setPaymentStatus(dto.getPaymentStatus());
        sale.setTotalAmount(dto.getTotalAmount());

        Sales updated = salesRepository.save(sale);

        saveSalesItems(updated, dto.getItems());

        return mapToDTO(updated);
    }

    // DELETE
    @Override
    public void deleteSale(Long id) {

        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));

        salesRepository.delete(sale);
    }

    private void saveSalesItems(Sales sale, List<SalesItemRequestDTO> items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        for (SalesItemRequestDTO itemDto : items) {
            if (itemDto.getProductId() == null) {
                continue;
            }

            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemDto.getProductId()));

            int quantity = itemDto.getQuantity() != null ? itemDto.getQuantity() : 0;
            if (product.getStockQuantity() != null && product.getStockQuantity() < quantity) {
                throw new RuntimeException("Not enough stock available for product: " + product.getProductName());
            }

            BigDecimal sellingPrice = product.getSellingPrice() != null ? product.getSellingPrice() : BigDecimal.ZERO;
            BigDecimal total = sellingPrice.multiply(BigDecimal.valueOf(quantity));

            SalesItem item = new SalesItem();
            item.setSaleId(sale.getSaleId());
            item.setProductId(itemDto.getProductId());
            item.setQuantity(quantity);
            item.setSellingPrice(sellingPrice);
            item.setTotal(total);

            salesItemRepository.save(item);

            if (product.getStockQuantity() != null) {
                product.setStockQuantity(product.getStockQuantity() - quantity);
                productRepository.save(product);
            }
        }
    }

    // MAPPER METHOD
    private SalesResponseDTO mapToDTO(Sales sale) {

        SalesResponseDTO dto = new SalesResponseDTO();

        dto.setSaleId(sale.getSaleId());
        dto.setCustomerId(sale.getCustomerId());

        if (sale.getCustomerId() != null) {
            customerRepository.findById(sale.getCustomerId())
                    .ifPresent(customer -> dto.setCustomerName(customer.getCustomerName()));
        }

        dto.setCreatedBy(sale.getCreatedBy());
        dto.setInvoiceNumber(sale.getInvoiceNumber());
        dto.setPaymentStatus(sale.getPaymentStatus());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setSaleDate(sale.getSaleDate());

        List<SalesItem> salesItems = salesItemRepository.findBySaleId(sale.getSaleId());
        if (salesItems != null && !salesItems.isEmpty()) {
            List<SalesItemResponseDTO> itemDTOs = salesItems.stream().map(item -> {
                SalesItemResponseDTO itemDTO = new SalesItemResponseDTO();
                itemDTO.setSaleItemId(item.getSaleItemId());
                itemDTO.setSaleId(item.getSaleId());
                itemDTO.setProductId(item.getProductId());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setSellingPrice(item.getSellingPrice());
                itemDTO.setTotal(item.getTotal());
                return itemDTO;
            }).collect(Collectors.toList());
            dto.setItems(itemDTOs);
        }

        return dto;
    }
}