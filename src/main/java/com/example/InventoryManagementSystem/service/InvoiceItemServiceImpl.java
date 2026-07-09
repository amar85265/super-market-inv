package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.InvoiceItemRepository;
import com.example.InventoryManagementSystem.Repository.InvoiceRepository;
import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.dto.InvoiceItemRequestDto;
import com.example.InventoryManagementSystem.dto.InvoiceItemResponseDto;
import com.example.InventoryManagementSystem.model.Invoice;
import com.example.InventoryManagementSystem.model.InvoiceItem;
import com.example.InventoryManagementSystem.model.Product;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceItemServiceImpl implements InvoiceItemService {

    private final InvoiceItemRepository invoiceItemRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;

    @Override
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
    }

    @Override
    public List<InvoiceItemResponseDto> getAllInvoiceItems() {

        List<InvoiceItem> invoiceItems = invoiceItemRepository.findAll();

        return invoiceItems.stream()
                .map(item -> {

                    Product product = productRepository.findById(item.getProductId())
                            .orElse(null);

                    return convertToResponse(item, product);

                })
                .toList();
    }

    @Override
    public InvoiceItemResponseDto getInvoiceItemById(Long id) {

        InvoiceItem invoiceItem = invoiceItemRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Invoice Item not found with id : " + id));

        Product product = productRepository.findById(invoiceItem.getProductId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Product not found"));

        return convertToResponse(invoiceItem, product);
    }

    @Override
    public InvoiceItemResponseDto updateInvoiceItem(
            Long id,
            InvoiceItemRequestDto dto) {

        InvoiceItem invoiceItem = invoiceItemRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Invoice Item not found"));

        Product oldProduct = productRepository.findById(invoiceItem.getProductId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Old Product not found"));

        Product newProduct = productRepository.findById(dto.getProductId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Product not found"));

        // Restore previous stock
        oldProduct.setStockQuantity(
                oldProduct.getStockQuantity()
                        + invoiceItem.getQuantity().intValue());

        productRepository.save(oldProduct);

        if (newProduct.getStockQuantity() < dto.getQuantity().intValue()) {

            throw new RuntimeException(
                    "Insufficient stock");
        }

        BigDecimal quantity = dto.getQuantity();

        BigDecimal unitPrice = newProduct.getSellingPrice();

        BigDecimal discount = dto.getDiscount() == null
                ? BigDecimal.ZERO
                : dto.getDiscount();

        BigDecimal taxableAmount =
                unitPrice.multiply(quantity)
                        .subtract(discount);

        BigDecimal gst =
                newProduct.getGstPercentage();

        BigDecimal taxAmount =
                taxableAmount
                        .multiply(gst)
                        .divide(
                                new BigDecimal("100"),
                                2,
                                RoundingMode.HALF_UP);

        BigDecimal lineTotal =
                taxableAmount.add(taxAmount);

        invoiceItem.setInvoiceId(dto.getInvoiceId());

        invoiceItem.setProductId(newProduct.getProductId());

        invoiceItem.setProductName(newProduct.getProductName());

        invoiceItem.setBarcode(newProduct.getBarcode());

        invoiceItem.setQuantity(quantity);

        invoiceItem.setUnitPrice(unitPrice);

        invoiceItem.setDiscount(discount);

        invoiceItem.setTaxPercentage(gst);

        invoiceItem.setTaxAmount(taxAmount);

        invoiceItem.setLineTotal(lineTotal);

        InvoiceItem updated =
                invoiceItemRepository.save(invoiceItem);

        newProduct.setStockQuantity(
                newProduct.getStockQuantity()
                        - quantity.intValue());

        productRepository.save(newProduct);

        recalculateInvoiceTotals(dto.getInvoiceId());

        return convertToResponse(updated, newProduct);
    }

    @Override
    public void deleteInvoiceItem(Long id) {

        InvoiceItem invoiceItem = invoiceItemRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Invoice Item not found"));

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
    }
}