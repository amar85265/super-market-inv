package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.Repository.SalesItemRepository;
import com.example.InventoryManagementSystem.Repository.SalesReturnItemRepository;
import com.example.InventoryManagementSystem.Repository.SalesReturnRepository;
import com.example.InventoryManagementSystem.dto.SalesReturnItemRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesReturnItemResponseDTO;
import com.example.InventoryManagementSystem.exception.InventoryException;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.model.SalesItem;
import com.example.InventoryManagementSystem.model.SalesReturn;
import com.example.InventoryManagementSystem.model.SalesReturnItem;
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
public class SalesReturnItemServiceImpl implements SalesReturnItemService {

    private final SalesReturnItemRepository salesReturnItemRepository;
    private final ProductRepository productRepository;
    private final SalesReturnRepository salesReturnRepository;
    private final SalesItemRepository salesItemRepository;

    // ------------------- ID GENERATOR -------------------
    private synchronized String generateReturnItemId() {
        Optional<SalesReturnItem> lastItem =
                salesReturnItemRepository.findTopByOrderBySalesReturnItemIdDesc();

        if (lastItem.isEmpty()) {
            return "SRITEM-001";
        }

        String lastId = lastItem.get().getSalesReturnItemId();
        int lastHyphenPosition = lastId.lastIndexOf('-');
        if (lastHyphenPosition == -1) {
            return "SRITEM-001";
        }

        try {
            int lastNumber = Integer.parseInt(lastId.substring(lastHyphenPosition + 1));
            return String.format("SRITEM-%03d", lastNumber + 1);
        } catch (NumberFormatException e) {
            return "SRITEM-001";
        }
    }

    // ------------------- CREATE -------------------
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalesReturnItemResponseDTO createItem(SalesReturnItemRequestDTO dto) {
        validateRequest(dto);

        SalesReturn salesReturn = getSalesReturnOrThrow(dto.getSalesReturnId());

        // ✅ NEW: Verify that the provided saleId matches the return's saleId
        validateSaleIdMatchesReturn(dto.getSaleId(), salesReturn);

        checkReturnIsNotRefunded(salesReturn);

        Product product = getActiveProductOrThrow(dto.getProductId());

        // Get original sale item to fetch the correct historical selling price
        SalesItem originalSaleItem = getOriginalSaleItemOrThrow(
                salesReturn.getSaleId(), dto.getProductId());

        checkReturnQuantityIsValid(
                salesReturn.getSaleId(),
                dto.getProductId(),
                dto.getQuantity(),
                null
        );

        checkProductNotAlreadyInReturn(dto.getSalesReturnId(), dto.getProductId());

        // Use the price from the original sale
        BigDecimal unitPrice = originalSaleItem.getSellingPrice();
        BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(dto.getQuantity()));

        increaseProductStock(product, dto.getQuantity());

        SalesReturnItem newItem = buildReturnItem(dto, unitPrice, lineTotal);
        SalesReturnItem savedItem = salesReturnItemRepository.save(newItem);

        updateReturnTotalAmount(salesReturn);
        return mapToResponseDTO(savedItem);
    }

    // ------------------- GET ALL -------------------
    @Override
    public List<SalesReturnItemResponseDTO> getAll() {
        return salesReturnItemRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ------------------- GET BY ID -------------------
    @Override
    public SalesReturnItemResponseDTO getById(String salesReturnItemId) {
        if (salesReturnItemId == null || salesReturnItemId.isBlank()) {
            throw new InventoryException("Sales Return Item ID is required");
        }
        SalesReturnItem item = salesReturnItemRepository.findById(salesReturnItemId)
                .orElseThrow(() -> new InventoryException(
                        "Sales Return Item not found with ID: " + salesReturnItemId));
        return mapToResponseDTO(item);
    }

    // ------------------- GET BY RETURN ID -------------------
    @Override
    public List<SalesReturnItemResponseDTO> getByReturnId(String salesReturnId) {
        if (salesReturnId == null || salesReturnId.isBlank()) {
            throw new InventoryException("Sales Return ID is required");
        }
        getSalesReturnOrThrow(salesReturnId);
        return salesReturnItemRepository.findBySalesReturnId(salesReturnId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ------------------- UPDATE -------------------
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalesReturnItemResponseDTO updateItem(String salesReturnItemId,
                                                 SalesReturnItemRequestDTO dto) {
        if (salesReturnItemId == null || salesReturnItemId.isBlank()) {
            throw new InventoryException("Sales Return Item ID is required");
        }
        validateRequest(dto);

        SalesReturnItem existingItem = salesReturnItemRepository.findById(salesReturnItemId)
                .orElseThrow(() -> new InventoryException(
                        "Sales Return Item not found with ID: " + salesReturnItemId));

        SalesReturn salesReturn = getSalesReturnOrThrow(dto.getSalesReturnId());

        // ✅ NEW: Verify saleId matches
        validateSaleIdMatchesReturn(dto.getSaleId(), salesReturn);

        checkReturnIsNotRefunded(salesReturn);

        Product newProduct = getActiveProductOrThrow(dto.getProductId());

        // Get original sale item for the new product
        SalesItem originalSaleItem = getOriginalSaleItemOrThrow(
                salesReturn.getSaleId(), dto.getProductId());

        checkProductNotAlreadyInReturnExcludingCurrentItem(
                dto.getSalesReturnId(), dto.getProductId(), existingItem.getSalesReturnItemId());

        // Reverse old stock
        Product oldProduct = getProductOrThrow(existingItem.getProductId());
        decreaseProductStock(oldProduct, existingItem.getQuantity());

        // Validate new quantity
        checkReturnQuantityIsValid(
                salesReturn.getSaleId(),
                dto.getProductId(),
                dto.getQuantity(),
                existingItem.getSalesReturnItemId()
        );

        // Apply new stock
        increaseProductStock(newProduct, dto.getQuantity());

        // Use the price from the original sale
        BigDecimal newUnitPrice = originalSaleItem.getSellingPrice();
        BigDecimal newLineTotal = newUnitPrice.multiply(BigDecimal.valueOf(dto.getQuantity()));

        existingItem.setSalesReturnId(dto.getSalesReturnId());
        existingItem.setProductId(dto.getProductId());
        existingItem.setQuantity(dto.getQuantity());
        existingItem.setPrice(newUnitPrice);
        existingItem.setTotal(newLineTotal);

        SalesReturnItem savedItem = salesReturnItemRepository.save(existingItem);
        updateReturnTotalAmount(salesReturn);

        return mapToResponseDTO(savedItem);
    }

    // ------------------- DELETE -------------------
    @Override
    @Transactional
    public void deleteItem(String salesReturnItemId) {
        if (salesReturnItemId == null || salesReturnItemId.isBlank()) {
            throw new InventoryException("Sales Return Item ID is required");
        }

        SalesReturnItem itemToDelete = salesReturnItemRepository.findById(salesReturnItemId)
                .orElseThrow(() -> new InventoryException(
                        "Sales Return Item not found with ID: " + salesReturnItemId));

        SalesReturn salesReturn = getSalesReturnOrThrow(itemToDelete.getSalesReturnId());
        checkReturnIsNotRefunded(salesReturn);

        Product product = getProductOrThrow(itemToDelete.getProductId());
        decreaseProductStock(product, itemToDelete.getQuantity());

        salesReturnItemRepository.delete(itemToDelete);
        updateReturnTotalAmount(salesReturn);
    }

    // ------------------- PRIVATE HELPERS -------------------

    private SalesReturn getSalesReturnOrThrow(String salesReturnId) {
        return salesReturnRepository.findById(parseLongId(salesReturnId))
                .orElseThrow(() -> new InventoryException(
                        "Sales Return not found with ID: " + salesReturnId));
    }

    private Product getProductOrThrow(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new InventoryException(
                        "Product not found with ID: " + productId));
    }

    private Product getActiveProductOrThrow(String productId) {
        Product product = getProductOrThrow(productId);
        if (!"active".equalsIgnoreCase(product.getStatus())) {
            throw new InventoryException(
                    "Product '" + product.getProductName() + "' is inactive and cannot be returned");
        }
        return product;
    }

    private SalesItem getOriginalSaleItemOrThrow(String saleId, String productId) {
        return salesItemRepository
                .findBySale_SaleIdAndProduct_ProductId(saleId, productId)
                .orElseThrow(() -> new InventoryException(
                        "Product " + productId + " was not part of sale " + saleId +
                                ". Cannot be returned."));
    }

    // ✅ NEW: Validate that the provided saleId matches the return's saleId
    private void validateSaleIdMatchesReturn(String providedSaleId, SalesReturn salesReturn) {
        if (!salesReturn.getSaleId().equals(providedSaleId)) {
            throw new InventoryException(
                    "Sale ID mismatch: Return belongs to sale " + salesReturn.getSaleId()
                            + " but request provided " + providedSaleId
            );
        }
    }

    private void validateRequest(SalesReturnItemRequestDTO dto) {
        if (dto == null) {
            throw new InventoryException("Request body cannot be null");
        }
        if (dto.getSalesReturnId() == null || dto.getSalesReturnId().isBlank()) {
            throw new InventoryException("Sales Return ID is required");
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

    private void checkReturnIsNotRefunded(SalesReturn salesReturn) {
        if ("REFUNDED".equalsIgnoreCase(salesReturn.getRefundStatus())) {
            throw new InventoryException(
                    "This return has already been refunded and cannot be modified");
        }
    }

    private void checkProductNotAlreadyInReturn(String salesReturnId, String productId) {
        salesReturnItemRepository
                .findBySalesReturnIdAndProductId(salesReturnId, productId)
                .ifPresent(existing -> {
                    throw new InventoryException(
                            "Product already exists in this return. Please update that line item instead.");
                });
    }

    private void checkProductNotAlreadyInReturnExcludingCurrentItem(
            String salesReturnId, String productId, String currentItemId) {
        salesReturnItemRepository
                .findBySalesReturnIdAndProductId(salesReturnId, productId)
                .filter(other -> !other.getSalesReturnItemId().equals(currentItemId))
                .ifPresent(other -> {
                    throw new InventoryException(
                            "Product already exists in this return. Please update that line item instead.");
                });
    }

    private void checkReturnQuantityIsValid(String saleId, String productId,
                                            Integer requestedQty, String excludeItemId) {
        SalesItem originalSaleItem = salesItemRepository
                .findBySale_SaleIdAndProduct_ProductId(saleId, productId)
                .orElseThrow(() -> new InventoryException(
                        "Product " + productId + " was not part of sale " + saleId
                                + ". Cannot be returned."));

        int originalSoldQuantity = originalSaleItem.getQuantity();

        Integer alreadyReturnedRaw = salesReturnItemRepository
                .getTotalReturnedQuantityForProductInSale(productId, saleId);
        int alreadyReturned = (alreadyReturnedRaw == null) ? 0 : alreadyReturnedRaw;

        if (excludeItemId != null) {
            SalesReturnItem currentItem = salesReturnItemRepository
                    .findById(excludeItemId).orElse(null);
            if (currentItem != null) {
                alreadyReturned -= currentItem.getQuantity();
            }
        }

        int remaining = originalSoldQuantity - alreadyReturned;
        if (requestedQty > remaining) {
            throw new InventoryException(
                    "Cannot return " + requestedQty + " units. "
                            + "Only " + remaining + " units are still returnable for this product in sale " + saleId);
        }
    }

    private void increaseProductStock(Product product, int quantity) {
        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);
    }

    private void decreaseProductStock(Product product, int quantity) {
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }

    private void updateReturnTotalAmount(SalesReturn salesReturn) {
        String returnIdAsString = String.valueOf(salesReturn.getReturnId());
        BigDecimal newTotal = salesReturnItemRepository
                .findBySalesReturnId(returnIdAsString)
                .stream()
                .map(SalesReturnItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        salesReturn.setTotalAmount(newTotal);
        salesReturnRepository.save(salesReturn);
    }

    private SalesReturnItem buildReturnItem(SalesReturnItemRequestDTO dto,
                                            BigDecimal price, BigDecimal total) {
        SalesReturnItem item = new SalesReturnItem();
        item.setSalesReturnItemId(generateReturnItemId());
        item.setSalesReturnId(dto.getSalesReturnId());
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setPrice(price);
        item.setTotal(total);
        return item;
    }

    private SalesReturnItemResponseDTO mapToResponseDTO(SalesReturnItem item) {
        SalesReturnItemResponseDTO response = new SalesReturnItemResponseDTO();
        response.setSalesReturnItemId(item.getSalesReturnItemId());
        response.setSalesReturnId(item.getSalesReturnId());
        response.setProductId(item.getProductId());
        response.setQuantity(item.getQuantity());
        response.setPrice(item.getPrice());
        response.setTotal(item.getTotal());

        productRepository.findById(item.getProductId())
                .ifPresent(product -> response.setProductName(product.getProductName()));

        return response;
    }

    private Long parseLongId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InventoryException("Invalid Sales Return ID format: " + id);
        }
    }
}