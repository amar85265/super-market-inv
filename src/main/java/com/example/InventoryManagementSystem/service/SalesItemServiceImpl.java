package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.Repository.SalesItemRepository;
import com.example.InventoryManagementSystem.Repository.SalesRepository;
import com.example.InventoryManagementSystem.dto.SalesItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesItemResponseDTO;
import com.example.InventoryManagementSystem.exception.InventoryException;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.model.Sales;
import com.example.InventoryManagementSystem.model.SalesItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesItemServiceImpl implements SalesItemService {

    private final SalesItemRepository salesItemRepository;
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;

    // ----- ID Generator -----
    // NOTE: SERIALIZABLE isolation here prevents two concurrent requests
    // from reading the same "last ID" and generating a duplicate.
    // For high-traffic systems, prefer a DB sequence instead of string parsing.
    private synchronized String generateSaleItemId() {
        Optional<SalesItem> last = salesItemRepository.findTopByOrderBySaleItemIdDesc();
        if (last.isEmpty()) {
            return "SITEM-001";
        }
        String lastId = last.get().getSaleItemId();
        int hyphenIndex = lastId.lastIndexOf('-');
        if (hyphenIndex == -1) {
            return "SITEM-001";
        }
        try {
            String numPart = lastId.substring(hyphenIndex + 1);
            int num = Integer.parseInt(numPart);
            return String.format("SITEM-%03d", num + 1);
        } catch (NumberFormatException e) {
            return "SITEM-001";
        }
    }

    // ----- Basic field validation -----
    private void validateRequest(SalesItemRequestDTO dto) {
        if (dto == null) {
            throw new InventoryException("Request body cannot be null");
        }
        if (dto.getSaleId() == null || dto.getSaleId().isBlank()) {
            throw new InventoryException("Sale ID is required");
        }
        if (dto.getProductId() == null || dto.getProductId().isBlank()) {
            throw new InventoryException("Product ID is required");
        }
        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new InventoryException("Quantity must be greater than 0");
        }
    }

    // ----- Sale state check -----
    private void validateSaleModifiable(Sales sale) {
        String status = sale.getPaymentStatus();
        if ("COMPLETED".equalsIgnoreCase(status) || "CANCELLED".equalsIgnoreCase(status)) {
            throw new InventoryException("Cannot modify items of a sale with status: " + status);
        }
    }

    // ----- Stock / price validation -----
    private void validateStock(Product product, Integer qty) {
        if (qty == null || qty <= 0) {
            throw new InventoryException("Quantity must be greater than 0");
        }
        if (product.getStockQuantity() == null || product.getStockQuantity() <= 0) {
            throw new InventoryException("Product '" + product.getProductName() + "' is out of stock");
        }
        if (product.getStockQuantity() < qty) {
            throw new InventoryException(
                    "Only " + product.getStockQuantity() + " units available for '" + product.getProductName() + "'");
        }
        if (product.getSellingPrice() == null || product.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InventoryException("Product '" + product.getProductName() + "' has an invalid selling price");
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalesItemResponseDTO createSalesItem(SalesItemRequestDTO dto) {
        validateRequest(dto);

        Sales sale = salesRepository.findById(dto.getSaleId())
                .orElseThrow(() -> new InventoryException("Sale not found with id: " + dto.getSaleId()));
        validateSaleModifiable(sale);

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new InventoryException("Product not found with id: " + dto.getProductId()));

        if (!"active".equalsIgnoreCase(product.getStatus())) {
            throw new InventoryException("Product is inactive and cannot be sold");
        }

        salesItemRepository.findBySale_SaleIdAndProduct_ProductId(dto.getSaleId(), dto.getProductId())
                .ifPresent(s -> {
                    throw new InventoryException("Product already exists in this sale. Use update to change quantity.");
                });

        validateStock(product, dto.getQuantity());

        product.setStockQuantity(product.getStockQuantity() - dto.getQuantity());
        productRepository.save(product);

        BigDecimal total = product.getSellingPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        SalesItem item = new SalesItem();
        item.setSaleItemId(generateSaleItemId());
        item.setSale(sale);
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setSellingPrice(product.getSellingPrice());
        item.setTotal(total);

        return mapToDTO(salesItemRepository.save(item));
    }

    @Override
    public List<SalesItemResponseDTO> getAllSalesItems() {
        return salesItemRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SalesItemResponseDTO getSalesItemById(String id) {
        if (id == null || id.isBlank()) {
            throw new InventoryException("Sales item ID is required");
        }
        SalesItem item = salesItemRepository.findById(id)
                .orElseThrow(() -> new InventoryException("Sales item not found with id: " + id));
        return mapToDTO(item);
    }

    @Override
    public List<SalesItemResponseDTO> getItemsBySaleId(String saleId) {
        if (saleId == null || saleId.isBlank()) {
            throw new InventoryException("Sale ID is required");
        }
        salesRepository.findById(saleId)
                .orElseThrow(() -> new InventoryException("Sale not found with id: " + saleId));
        return salesItemRepository.findBySale_SaleId(saleId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalesItemResponseDTO updateSalesItem(String id, SalesItemRequestDTO dto) {
        if (id == null || id.isBlank()) {
            throw new InventoryException("Sales item ID is required");
        }
        validateRequest(dto);

        // 1. Existing item
        SalesItem existing = salesItemRepository.findById(id)
                .orElseThrow(() -> new InventoryException("Sales item not found with id: " + id));

        // 2. Current sale must be modifiable
        validateSaleModifiable(existing.getSale());

        // 3. Resolve target sale (validate BEFORE touching any stock)
        Sales newSale = salesRepository.findById(dto.getSaleId())
                .orElseThrow(() -> new InventoryException("Sale not found with id: " + dto.getSaleId()));
        validateSaleModifiable(newSale);

        // 4. Resolve target product (validate BEFORE touching any stock)
        Product newProduct = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new InventoryException("Product not found with id: " + dto.getProductId()));
        if (!"active".equalsIgnoreCase(newProduct.getStatus())) {
            throw new InventoryException("Product is inactive and cannot be sold");
        }

        // 5. Prevent duplicate product within the same sale (excluding this item itself)
        salesItemRepository.findBySale_SaleIdAndProduct_ProductId(dto.getSaleId(), dto.getProductId())
                .filter(other -> !other.getSaleItemId().equals(existing.getSaleItemId()))
                .ifPresent(other -> {
                    throw new InventoryException("Product already exists in this sale. Use that line item to change quantity.");
                });

        // 6. Restore old stock only after all validation has passed
        Product oldProduct = existing.getProduct();
        oldProduct.setStockQuantity(oldProduct.getStockQuantity() + existing.getQuantity());
        productRepository.save(oldProduct);

        // 7. Re-check stock for the new product/quantity.
        //    If oldProduct == newProduct, oldProduct's stock was just restored above,
        //    so newProduct (same row) already reflects the restored quantity.
        validateStock(newProduct, dto.getQuantity());

        // 8. Deduct new stock
        newProduct.setStockQuantity(newProduct.getStockQuantity() - dto.getQuantity());
        productRepository.save(newProduct);

        // 9. Update item
        BigDecimal total = newProduct.getSellingPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));
        existing.setSale(newSale);
        existing.setProduct(newProduct);
        existing.setQuantity(dto.getQuantity());
        existing.setSellingPrice(newProduct.getSellingPrice());
        existing.setTotal(total);

        return mapToDTO(salesItemRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteSalesItem(String id) {
        if (id == null || id.isBlank()) {
            throw new InventoryException("Sales item ID is required");
        }
        SalesItem item = salesItemRepository.findById(id)
                .orElseThrow(() -> new InventoryException("Sales item not found with id: " + id));

        validateSaleModifiable(item.getSale());

        Product product = item.getProduct();
        product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
        productRepository.save(product);

        salesItemRepository.delete(item);
    }

    // ----- Mapper -----
    private SalesItemResponseDTO mapToDTO(SalesItem item) {
        SalesItemResponseDTO dto = new SalesItemResponseDTO();
        dto.setSaleItemId(item.getSaleItemId());
        dto.setSaleId(item.getSale().getSaleId());
        dto.setProductId(item.getProduct().getProductId());
        dto.setProductName(item.getProduct().getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setSellingPrice(item.getSellingPrice());
        dto.setTotal(item.getTotal());
        return dto;
    }
}