package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.*;
import com.example.InventoryManagementSystem.model.*;
import com.example.InventoryManagementSystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final SalesRepository salesRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final SalesItemRepository salesItemRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    // Auto-generate SAL-001, SAL-002...
    private String generateSaleId() {
        Optional<Sales> last =
                salesRepository.findTopByOrderBySaleIdDesc();
        if (last.isEmpty()) return "SAL-001";
        String lastId = last.get().getSaleId();
        int num = Integer.parseInt(lastId.substring(4));
        return String.format("SAL-%03d", num + 1);
    }

    // Auto-generate SITEM-001, SITEM-002...
    private String generateSaleItemId() {
        Optional<SalesItem> last =
                salesItemRepository.findTopByOrderBySaleItemIdDesc();
        if (last.isEmpty()) return "SITEM-001";
        String lastId = last.get().getSaleItemId();
        int num = Integer.parseInt(lastId.substring(6));
        return String.format("SITEM-%03d", num + 1);
    }

    // Auto-generate SMOV-001, SMOV-002...
    private String generateMovementId() {
        Optional<StockMovement> last =
                stockMovementRepository.findTopByOrderByMovementIdDesc();
        if (last.isEmpty()) return "SMOV-001";
        String lastId = last.get().getMovementId();
        int num = Integer.parseInt(lastId.substring(5));
        return String.format("SMOV-%03d", num + 1);
    }

    @Override
    @Transactional
    public SalesResponseDTO createSale(SalesRequestDTO dto) {

        // Step 1 — Validate customer
        Customer customer = customerRepository
                .findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found: " + dto.getCustomerId()));

        // Step 2 — Validate user
        User user = userRepository
                .findById(dto.getCreatedBy())
                .orElseThrow(() -> new RuntimeException(
                        "User not found: " + dto.getCreatedBy()));

        // Step 3 — Validate duplicate invoice
        if (salesRepository.existsByInvoiceNumber(
                dto.getInvoiceNumber())) {
            throw new RuntimeException(
                    "Invoice already exists: " + dto.getInvoiceNumber());
        }

        // Step 4 — Create Sale
        Sales sale = new Sales();
        sale.setSaleId(generateSaleId());
        sale.setCustomer(customer);
        sale.setCreatedBy(user);
        sale.setInvoiceNumber(dto.getInvoiceNumber());
        sale.setPaymentStatus(dto.getPaymentStatus());
        sale.setTotalAmount(BigDecimal.ZERO);
        Sales savedSale = salesRepository.save(sale);

        // Step 5 — Process each item
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<SalesItem> savedItems = new ArrayList<>();



        for (SalesItemRequestDTO itemDTO : dto.getItems()) {

            // Find product
            Product product = productRepository
                    .findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException(
                            "Product not found: "
                                    + itemDTO.getProductId()));

            // Check stock available
            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for product: "
                                + product.getProductName()
                                + ". Available: "
                                + product.getStockQuantity()
                                + ", Requested: "
                                + itemDTO.getQuantity());
            }

            // Calculate total for this item
            BigDecimal itemTotal = product.getSellingPrice()
                    .multiply(BigDecimal.valueOf(
                            itemDTO.getQuantity()));

            // Save sale item
            SalesItem saleItem = new SalesItem();
            saleItem.setSaleItemId(generateSaleItemId());
            saleItem.setSale(savedSale);
            saleItem.setProduct(product);
            saleItem.setQuantity(itemDTO.getQuantity());
            saleItem.setSellingPrice(product.getSellingPrice());
            saleItem.setTotal(itemTotal);
            savedItems.add(salesItemRepository.save(saleItem));

            // Step 6 — Deduct stock
            product.setStockQuantity(
                    product.getStockQuantity() - itemDTO.getQuantity());
            productRepository.save(product);

            // Step 7 — Record stock movement
            StockMovement movement = StockMovement.builder()
                    .movementId(generateMovementId())
                    .product(product)
                    .movementType("OUT")
                    .quantity(itemDTO.getQuantity())
                    .referenceId(savedSale.getSaleId())
                    .notes("Sale: " + savedSale.getInvoiceNumber())
                    .build();
            stockMovementRepository.save(movement);

            totalAmount = totalAmount.add(itemTotal);
        }

        // Step 8 — Update total amount
        savedSale.setTotalAmount(totalAmount);
        salesRepository.save(savedSale);

        return mapToDTO(savedSale, savedItems);
    }

    @Override
    public SalesResponseDTO getSaleById(String id) {
        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Sale not found: " + id));
        List<SalesItem> items =
                salesItemRepository.findBySale_SaleId(id);
        return mapToDTO(sale, items);
    }

    @Override
    public List<SalesResponseDTO> getAllSales() {
        return salesRepository.findAll()
                .stream()
                .map(sale -> mapToDTO(sale,
                        salesItemRepository.findBySale_SaleId(
                                sale.getSaleId())))
                .collect(Collectors.toList());
    }

    @Override
    public SalesResponseDTO updateSale(String id,
                                       SalesRequestDTO dto) {
        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Sale not found: " + id));

        Customer customer = customerRepository
                .findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found: " + dto.getCustomerId()));

        User user = userRepository
                .findById(dto.getCreatedBy())
                .orElseThrow(() -> new RuntimeException(
                        "User not found: " + dto.getCreatedBy()));

        sale.setCustomer(customer);
        sale.setCreatedBy(user);
        sale.setInvoiceNumber(dto.getInvoiceNumber());
        sale.setPaymentStatus(dto.getPaymentStatus());

        List<SalesItem> items =
                salesItemRepository.findBySale_SaleId(id);
        return mapToDTO(salesRepository.save(sale), items);
    }

    @Override
    public void deleteSale(String id) {
        Sales sale = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Sale not found: " + id));
        salesRepository.delete(sale);
    }

    private SalesResponseDTO mapToDTO(Sales sale,
                                      List<SalesItem> items) {
        SalesResponseDTO dto = new SalesResponseDTO();
        dto.setSaleId(sale.getSaleId());
        dto.setCustomerId(sale.getCustomer().getCustomerId());
        dto.setCustomerName(sale.getCustomer().getCustomerName());
        dto.setCreatedBy(sale.getCreatedBy().getUserId());
        dto.setInvoiceNumber(sale.getInvoiceNumber());
        dto.setPaymentStatus(sale.getPaymentStatus());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setSaleDate(sale.getSaleDate());
        dto.setItems(items.stream()
                .map(this::mapItemToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private SalesItemResponseDTO mapItemToDTO(SalesItem item) {
        SalesItemResponseDTO dto = new SalesItemResponseDTO();
        dto.setSaleItemId(item.getSaleItemId());
        dto.setProductId(item.getProduct().getProductId());
        dto.setProductName(item.getProduct().getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setSellingPrice(item.getSellingPrice());
        dto.setTotal(item.getTotal());
        return dto;
    }
}