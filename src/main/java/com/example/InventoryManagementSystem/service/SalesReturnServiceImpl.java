package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.CustomerRepository;
import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.Repository.SalesItemRepository;
import com.example.InventoryManagementSystem.Repository.SalesRepository;
import com.example.InventoryManagementSystem.Repository.SalesReturnRepository;
import com.example.InventoryManagementSystem.dto.SalesReturnRequestDTO;
import com.example.InventoryManagementSystem.dto.SalesReturnResponseDTO;
import com.example.InventoryManagementSystem.exception.InventoryException;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.model.SalesItem;
import com.example.InventoryManagementSystem.model.SalesReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesReturnServiceImpl implements SalesReturnService {

    private final SalesReturnRepository repo;
    private final SalesItemRepository salesItemRepository;
    private final SalesRepository salesRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalesReturnResponseDTO createReturn(SalesReturnRequestDTO dto) {

        // 1. Basic field validation
        validateBasic(dto);

        // 2. Sale, Customer must exist
        if (!salesRepository.existsById(dto.getSaleId())) {
            throw new InventoryException("Sale ID not found: " + dto.getSaleId());
        }
        if (!customerRepository.existsById(dto.getCustomerId())) {
            throw new InventoryException("Customer ID not found: " + dto.getCustomerId());
        }

        // 3. Sales Item must exist, and must belong to this Sale ID
        SalesItem salesItem = salesItemRepository.findById(dto.getSalesItemId())
                .orElseThrow(() -> new InventoryException("Sales Item not found with id: " + dto.getSalesItemId()));

        if (!salesItem.getSale().getSaleId().equals(dto.getSaleId())) {
            throw new InventoryException(
                    "Sales Item " + dto.getSalesItemId() + " does not belong to Sale " + dto.getSaleId());
        }

        // 4. Return quantity cannot exceed (originally sold - already returned for this sales item)
        int alreadyReturned = repo.findAll().stream()
                .filter(r -> r.getSalesItemId().equals(dto.getSalesItemId()))
                .mapToInt(SalesReturn::getReturnQuantity)
                .sum();

        int remainingReturnable = salesItem.getQuantity() - alreadyReturned;
        if (dto.getReturnQuantity() > remainingReturnable) {
            throw new InventoryException(
                    "Cannot return " + dto.getReturnQuantity() + " units. Only " + remainingReturnable
                            + " units remain returnable for sales item " + dto.getSalesItemId());
        }

        // 5. Compute total from the product's price at time of original sale (not client-supplied)
        Product product = salesItem.getProduct();
        BigDecimal unitPrice = salesItem.getSellingPrice();
        BigDecimal computedTotal = unitPrice.multiply(BigDecimal.valueOf(dto.getReturnQuantity()));

        // 6. STOCK INCREASES — product physically comes back into inventory
        product.setStockQuantity(product.getStockQuantity() + dto.getReturnQuantity());
        productRepository.save(product);

        // 7. Build and save the return record
        SalesReturn entity = SalesReturn.builder()
                .salesItemId(dto.getSalesItemId())
                .saleId(dto.getSaleId())
                .customerId(dto.getCustomerId())
                .returnQuantity(dto.getReturnQuantity())
                .reason(dto.getReason())
                .notes(dto.getNotes())
                .totalAmount(computedTotal)
                .refundStatus("PENDING") // always starts PENDING regardless of client input
                .build();

        return map(repo.save(entity));
    }

    @Override
    public SalesReturnResponseDTO getById(String id) {
        SalesReturn entity = repo.findById(parseId(id))
                .orElseThrow(() -> new InventoryException("Sales Return not found with id: " + id));
        return map(entity);
    }

    @Override
    public List<SalesReturnResponseDTO> getAll() {
        return repo.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public SalesReturnResponseDTO updateReturn(String id, SalesReturnRequestDTO dto) {
        validateBasic(dto);

        SalesReturn entity = repo.findById(parseId(id))
                .orElseThrow(() -> new InventoryException("Sales Return not found with id: " + id));

        if ("REFUNDED".equalsIgnoreCase(entity.getRefundStatus())) {
            throw new InventoryException("Cannot modify a return that has already been refunded");
        }

        SalesItem salesItem = salesItemRepository.findById(dto.getSalesItemId())
                .orElseThrow(() -> new InventoryException("Sales Item not found with id: " + dto.getSalesItemId()));

        if (!salesItem.getSale().getSaleId().equals(dto.getSaleId())) {
            throw new InventoryException(
                    "Sales Item " + dto.getSalesItemId() + " does not belong to Sale " + dto.getSaleId());
        }

        // STEP 1: reverse OLD stock effect first
        Product oldProduct = productRepository.findById(
                        salesItemRepository.findById(entity.getSalesItemId())
                                .orElseThrow(() -> new InventoryException("Original sales item not found"))
                                .getProduct().getProductId())
                .orElseThrow(() -> new InventoryException("Original product not found"));
        oldProduct.setStockQuantity(oldProduct.getStockQuantity() - entity.getReturnQuantity());
        productRepository.save(oldProduct);

        // STEP 2: re-validate remaining returnable quantity (excluding this return's old contribution)
        int alreadyReturned = repo.findAll().stream()
                .filter(r -> r.getSalesItemId().equals(dto.getSalesItemId()))
                .filter(r -> !r.getReturnId().equals(entity.getReturnId()))
                .mapToInt(SalesReturn::getReturnQuantity)
                .sum();
        int remainingReturnable = salesItem.getQuantity() - alreadyReturned;
        if (dto.getReturnQuantity() > remainingReturnable) {
            throw new InventoryException(
                    "Cannot return " + dto.getReturnQuantity() + " units. Only " + remainingReturnable
                            + " units remain returnable for sales item " + dto.getSalesItemId());
        }

        // STEP 3: apply NEW stock effect
        Product newProduct = salesItem.getProduct();
        BigDecimal computedTotal = salesItem.getSellingPrice().multiply(BigDecimal.valueOf(dto.getReturnQuantity()));
        newProduct.setStockQuantity(newProduct.getStockQuantity() + dto.getReturnQuantity());
        productRepository.save(newProduct);

        entity.setSalesItemId(dto.getSalesItemId());
        entity.setSaleId(dto.getSaleId());
        entity.setCustomerId(dto.getCustomerId());
        entity.setReturnQuantity(dto.getReturnQuantity());
        entity.setReason(dto.getReason());
        entity.setNotes(dto.getNotes());
        entity.setTotalAmount(computedTotal);
        // refundStatus is updated separately via a dedicated status-change endpoint, not here

        return map(repo.save(entity));
    }

    @Override
    @Transactional
    public void delete(String id) {
        SalesReturn entity = repo.findById(parseId(id))
                .orElseThrow(() -> new InventoryException("Sales Return not found with id: " + id));

        if ("REFUNDED".equalsIgnoreCase(entity.getRefundStatus())) {
            throw new InventoryException("Cannot delete a return that has already been refunded");
        }

        // Deleting the return means it never happened — reverse the stock increase
        SalesItem salesItem = salesItemRepository.findById(entity.getSalesItemId())
                .orElseThrow(() -> new InventoryException("Sales Item not found"));
        Product product = salesItem.getProduct();
        product.setStockQuantity(product.getStockQuantity() - entity.getReturnQuantity());
        productRepository.save(product);

        repo.delete(entity);
    }

    private Long parseId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InventoryException("Invalid Sales Return ID: " + id);
        }
    }

    private void validateBasic(SalesReturnRequestDTO dto) {
        if (dto == null) {
            throw new InventoryException("Request body cannot be null");
        }
        if (dto.getSalesItemId() == null || dto.getSalesItemId().isBlank()) {
            throw new InventoryException("Sales Item ID is required");
        }
        if (dto.getSaleId() == null || dto.getSaleId().isBlank()) {
            throw new InventoryException("Sale ID is required");
        }
        if (dto.getCustomerId() == null || dto.getCustomerId().isBlank()) {
            throw new InventoryException("Customer ID is required");
        }
        if (dto.getReturnQuantity() == null || dto.getReturnQuantity() <= 0) {
            throw new InventoryException("Return quantity must be greater than 0");
        }
        if (dto.getReason() == null || dto.getReason().trim().isEmpty()) {
            throw new InventoryException("Reason is required");
        }
        if (dto.getNotes() != null && dto.getNotes().length() > 50) {
            throw new InventoryException("Notes cannot exceed 50 characters");
        }
    }

    private SalesReturnResponseDTO map(SalesReturn e) {
        return SalesReturnResponseDTO.builder()
                .returnId(e.getReturnId())
                .salesItemId(e.getSalesItemId())
                .saleId(e.getSaleId())
                .customerId(e.getCustomerId())
                .returnQuantity(e.getReturnQuantity())
                .reason(e.getReason())
                .notes(e.getNotes())
                .totalAmount(e.getTotalAmount())
                .refundStatus(e.getRefundStatus())
                .returnDate(e.getReturnDate())
                .createdAt(e.getCreatedAt())
                .build();
    }
}