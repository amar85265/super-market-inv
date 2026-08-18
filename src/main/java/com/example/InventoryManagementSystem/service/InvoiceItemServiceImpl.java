package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.CarServiceRepository;
import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.model.CarService;
import com.example.InventoryManagementSystem.model.InvoiceItem;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.Repository.InvoiceItemRepository;
import com.example.InventoryManagementSystem.service.InvoiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceItemServiceImpl implements InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;
    private final ProductRepository productRepository;
    private final CarServiceRepository carServiceRepository;

    // CREATE
    @Override
<<<<<<< Updated upstream
    public InvoiceItemResponseDto createInvoiceItem(InvoiceItemRequestDto dto) {

        Invoice invoice = invoiceRepository.findById(dto.getInvoiceId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Invoice not found : " + dto.getInvoiceId()));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Product not found : " + dto.getProductId()));

        if (product.getStockQuantity() == null ||
                product.getStockQuantity() <= 0) {

            throw new RuntimeException(
                    "Product is out of stock.");
        }

        BigDecimal quantity = dto.getQuantity();

        BigDecimal unitPrice = product.getSellingPrice();

        BigDecimal discount = dto.getDiscount() == null
                ? BigDecimal.ZERO
                : dto.getDiscount();

        BigDecimal taxableAmount =
                unitPrice.multiply(quantity)
                        .subtract(discount);

        BigDecimal gst =
                product.getGstPercentage();

        BigDecimal taxAmount =
                taxableAmount
                        .multiply(gst)
                        .divide(
                                new BigDecimal("100"),
                                2,
                                RoundingMode.HALF_UP);

        BigDecimal lineTotal =
                taxableAmount.add(taxAmount);

        InvoiceItem invoiceItem = new InvoiceItem();

        invoiceItem.setInvoiceId(invoice.getInvoiceId());

        invoiceItem.setProductId(product.getProductId());

        invoiceItem.setProductName(product.getProductName());

        invoiceItem.setBarcode(product.getBarcode());

        invoiceItem.setQuantity(quantity);

        invoiceItem.setUnitPrice(unitPrice);

        invoiceItem.setDiscount(discount);

        invoiceItem.setTaxPercentage(gst);

        invoiceItem.setTaxAmount(taxAmount);

        invoiceItem.setLineTotal(lineTotal);

        InvoiceItem saved =
                invoiceItemRepository.save(invoiceItem);

        int availableStock =
                product.getStockQuantity();

        int sold =
                quantity.intValue();

        if (sold > availableStock) {

            throw new RuntimeException(
                    "Insufficient stock.");
        }

        product.setStockQuantity(
                availableStock - sold);

        productRepository.save(product);

        recalculateInvoiceTotals(
                invoice.getInvoiceId());

        return convertToResponse(saved, product);
    }

    /**
     * Recalculate invoice totals
     */
    private void recalculateInvoiceTotals(Long invoiceId) {

        Invoice invoice =
                invoiceRepository.findById(invoiceId)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Invoice not found"));

        List<InvoiceItem> items =
                invoiceItemRepository.findByInvoiceId(invoiceId);

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;

        for (InvoiceItem item : items) {

            BigDecimal taxable =
                    item.getUnitPrice()
                            .multiply(item.getQuantity())
                            .subtract(item.getDiscount());

            subtotal = subtotal.add(taxable);

            tax = tax.add(item.getTaxAmount());
        }

        BigDecimal grandTotal =
                subtotal
                        .subtract(invoice.getDiscountAmount())
                        .add(tax);

        invoice.setSubtotal(subtotal);

        invoice.setTaxAmount(tax);

        invoice.setGrandTotal(grandTotal);

        invoice.setBalanceAmount(
                grandTotal.subtract(invoice.getPaidAmount()));

        invoiceRepository.save(invoice);
    }

    /**
     * Convert Entity to Response DTO
     */
    private InvoiceItemResponseDto convertToResponse(
            InvoiceItem invoiceItem,
            Product product) {

        InvoiceItemResponseDto dto =
                new InvoiceItemResponseDto();

        dto.setInvoiceItemId(
                invoiceItem.getInvoiceItemId());

        dto.setInvoiceId(
                invoiceItem.getInvoiceId());

        dto.setProductId(
                invoiceItem.getProductId());

        dto.setProductName(
                invoiceItem.getProductName());

        dto.setBarcode(
                invoiceItem.getBarcode());

        dto.setQuantity(
                invoiceItem.getQuantity());

        dto.setUnitPrice(
                invoiceItem.getUnitPrice());

        dto.setDiscount(
                invoiceItem.getDiscount());

        dto.setTaxPercentage(
                invoiceItem.getTaxPercentage());

        dto.setTaxAmount(
                invoiceItem.getTaxAmount());

        dto.setLineTotal(
                invoiceItem.getLineTotal());

        return dto;
=======
    public InvoiceItem createInvoiceItem(InvoiceItem invoiceItem) {
        populateDefaults(invoiceItem);
        return invoiceItemRepository.save(invoiceItem);
>>>>>>> Stashed changes
    }

    // GET ALL
    @Override
    public List<InvoiceItem> getAllInvoiceItems() {

        return invoiceItemRepository.findAll();
    }

    // GET BY ID
    @Override
<<<<<<< Updated upstream
    public InvoiceItemResponseDto getInvoiceItemById(Long id) {
=======
    public InvoiceItem getInvoiceItemById(String id) {
>>>>>>> Stashed changes

        return invoiceItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invoice item not found with ID: " + id
                        )
                );
    }

    // UPDATE
    @Override
<<<<<<< Updated upstream
    public InvoiceItemResponseDto updateInvoiceItem(
            Long id,
            InvoiceItemRequestDto dto) {
=======
    public InvoiceItem updateInvoiceItem(
            String id,
            InvoiceItem invoiceItem) {
>>>>>>> Stashed changes

        InvoiceItem existingItem =
                invoiceItemRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invoice item not found with ID: " + id
                                )
                        );

        existingItem.setItemType(invoiceItem.getItemType());
        existingItem.setProductId(invoiceItem.getProductId());
        existingItem.setServiceId(invoiceItem.getServiceId());
        existingItem.setDescription(invoiceItem.getDescription());
        existingItem.setUnit(invoiceItem.getUnit());
        existingItem.setQuantity(invoiceItem.getQuantity());
        existingItem.setRate(invoiceItem.getRate());

        populateDefaults(existingItem);

        return invoiceItemRepository.save(existingItem);
    }

    private void populateDefaults(InvoiceItem item) {

        if (item.getQuantity() == null) {
            item.setQuantity(BigDecimal.ONE);
        }

        if (item.getItemType() == null || item.getItemType().isBlank()) {
            if (item.getServiceId() != null && !item.getServiceId().isBlank()) {
                item.setItemType("SERVICE");
            } else {
                item.setItemType("PRODUCT");
            }
        }

        if (item.getUnit() == null || item.getUnit().isBlank()) {
            item.setUnit("SERVICE".equalsIgnoreCase(item.getItemType()) ? "JOB" : "PCS");
        }

        if (item.getRate() == null) {
            if ("SERVICE".equalsIgnoreCase(item.getItemType()) && item.getServiceId() != null) {
                item.setRate(carServiceRepository.findById(item.getServiceId())
                        .map(CarService::getPrice)
                        .orElseGet(() -> carServiceRepository.findByServiceCode(item.getServiceId())
                                .map(CarService::getPrice)
                                .orElse(new BigDecimal("500.00"))));
            } else if (item.getProductId() != null) {
                item.setRate(productRepository.findById(item.getProductId())
                        .map(p -> p.getSellingPrice() != null ? p.getSellingPrice() : new BigDecimal("100.00"))
                        .orElse(new BigDecimal("1500.00")));
            } else {
                item.setRate(new BigDecimal("100.00"));
            }
        }

        if (item.getDescription() == null || item.getDescription().isBlank()) {
            if ("SERVICE".equalsIgnoreCase(item.getItemType()) && item.getServiceId() != null) {
                item.setDescription(carServiceRepository.findById(item.getServiceId())
                        .map(CarService::getServiceName)
                        .orElse("Service (" + item.getServiceId() + ")"));
            } else if (item.getProductId() != null) {
                item.setDescription(productRepository.findById(item.getProductId())
                        .map(Product::getProductName)
                        .orElse("Product (" + item.getProductId() + ")"));
            } else {
                item.setDescription("Invoice Item");
            }
        }

        if (item.getQuantity() != null && item.getRate() != null) {
            item.setAmount(item.getQuantity().multiply(item.getRate()));
        } else if (item.getAmount() == null) {
            item.setAmount(BigDecimal.ZERO);
        }
    }

    // DELETE
    @Override
    public void deleteInvoiceItem(Long id) {

        InvoiceItem existingItem =
                invoiceItemRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invoice item not found with ID: " + id
                                )
                        );

<<<<<<< Updated upstream
        Product product = productRepository.findById(invoiceItem.getProductId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Product not found"));

        product.setStockQuantity(
                product.getStockQuantity()
                        + invoiceItem.getQuantity().intValue());

        productRepository.save(product);

        Long invoiceId = invoiceItem.getInvoiceId();

        invoiceItemRepository.delete(invoiceItem);

        recalculateInvoiceTotals(invoiceId);
=======
        invoiceItemRepository.delete(existingItem);
>>>>>>> Stashed changes
    }
}