package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.*;
import com.example.InventoryManagementSystem.Repository.CarServiceRepository;
import com.example.InventoryManagementSystem.Repository.InvoiceRepository;
import com.example.InventoryManagementSystem.Repository.ProductRepository;
import com.example.InventoryManagementSystem.model.CarService;
import com.example.InventoryManagementSystem.model.Invoice;
import com.example.InventoryManagementSystem.model.InvoiceItem;
import com.example.InventoryManagementSystem.model.Product;
import com.example.InventoryManagementSystem.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CarServiceRepository carServiceRepository;
    private final ProductRepository productRepository;

    private static final BigDecimal GST_RATE =
            new BigDecimal("0.18");

    private static final AtomicInteger invoiceSequence =
            new AtomicInteger(1);

    @Override
    @Transactional
    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO request) {

        // 1. Create Invoice
        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber(generateInvoiceNumber());

        invoice.setCustomerId(request.getCustomerId());
        invoice.setVehicleId(request.getVehicleId());
        invoice.setPaymentMethod(request.getPaymentMethod());

        if (request.getInvoiceDate() != null) {
            invoice.setInvoiceDate(request.getInvoiceDate());
        } else {
            invoice.setInvoiceDate(LocalDateTime.now());
        }

        invoice.setSubtotal(BigDecimal.ZERO);
        invoice.setTaxAmount(BigDecimal.ZERO);
        invoice.setGrandTotal(BigDecimal.ZERO);

        List<InvoiceItem> invoiceItems = new ArrayList<>();

        BigDecimal subtotal = BigDecimal.ZERO;

        // 2. Process every item
        for (InvoiceItemRequestDTO requestItem : request.getItems()) {

            InvoiceItem item = new InvoiceItem();

            item.setInvoice(invoice);

            String itemType =
                    requestItem.getItemType().toUpperCase();

<<<<<<< Updated upstream
        String invoiceNumber =
                "INV-" +
                        Year.now().getValue() +
                        "-" +
                        String.format("%05d", savedInvoice.getInvoiceId());
=======
            item.setItemType(itemType);
>>>>>>> Stashed changes

            BigDecimal rate;
            String description;
            String unit;

            if ("SERVICE".equals(itemType)) {

                if (requestItem.getServiceId() == null) {
                    throw new RuntimeException(
                            "Service ID is required for SERVICE item"
                    );
                }

                item.setServiceId(requestItem.getServiceId());

                rate = requestItem.getRate() != null ? requestItem.getRate() : getServicePrice(requestItem.getServiceId());
                description = (requestItem.getDescription() != null && !requestItem.getDescription().isBlank())
                        ? requestItem.getDescription()
                        : getServiceDescription(requestItem.getServiceId());
                unit = (requestItem.getUnit() != null && !requestItem.getUnit().isBlank())
                        ? requestItem.getUnit()
                        : "JOB";

            } else if ("PRODUCT".equals(itemType)) {

                if (requestItem.getProductId() == null) {
                    throw new RuntimeException(
                            "Product ID is required for PRODUCT item"
                    );
                }

                item.setProductId(requestItem.getProductId());

                rate = requestItem.getRate() != null ? requestItem.getRate() : getProductPrice(requestItem.getProductId());
                description = (requestItem.getDescription() != null && !requestItem.getDescription().isBlank())
                        ? requestItem.getDescription()
                        : getProductDescription(requestItem.getProductId());
                unit = (requestItem.getUnit() != null && !requestItem.getUnit().isBlank())
                        ? requestItem.getUnit()
                        : "PCS";

            } else {

                throw new RuntimeException(
                        "Invalid item type. Use SERVICE or PRODUCT"
                );
            }

            // 3. Calculate line amount
            BigDecimal quantity = requestItem.getQuantity();

            BigDecimal amount = rate.multiply(quantity)
                    .setScale(2, RoundingMode.HALF_UP);

            item.setDescription(description);
            item.setUnit(unit);
            item.setQuantity(quantity);
            item.setRate(rate);
            item.setAmount(amount);

            invoiceItems.add(item);

            // 4. Add to subtotal
            subtotal = subtotal.add(amount);
        }

        // 5. Calculate GST
        BigDecimal taxAmount = subtotal
                .multiply(GST_RATE)
                .setScale(2, RoundingMode.HALF_UP);

        // 6. Calculate grand total
        BigDecimal grandTotal = subtotal
                .add(taxAmount)
                .setScale(2, RoundingMode.HALF_UP);

        // 7. Set invoice totals
        invoice.setSubtotal(subtotal);
        invoice.setTaxAmount(taxAmount);
        invoice.setGrandTotal(grandTotal);

        invoice.setItems(invoiceItems);

        // 8. Save invoice + items
        Invoice savedInvoice =
                invoiceRepository.save(invoice);

        // 9. Return response
        return mapToResponse(savedInvoice);
    }

    @Override
<<<<<<< Updated upstream
    public InvoiceResponseDto getInvoiceById(Long id) {
=======
    public InvoiceResponseDTO getInvoice(String invoiceId) {
>>>>>>> Stashed changes

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invoice not found: " + invoiceId
                        ));

        return mapToResponse(invoice);
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoices() {

        return invoiceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
<<<<<<< Updated upstream
    public InvoiceResponseDto updateInvoice(Long invoiceId,
                                            InvoiceRequestDto dto) {
=======
    @Transactional
    public void deleteInvoice(String invoiceId) {
>>>>>>> Stashed changes

        if (!invoiceRepository.existsById(invoiceId)) {
            throw new RuntimeException(
                    "Invoice not found: " + invoiceId
            );
        }

        invoiceRepository.deleteById(invoiceId);
    }

<<<<<<< Updated upstream
    @Override
    public void deleteInvoice(Long id) {

        if (!invoiceRepository.existsById(id)) {
            throw new RuntimeException("Invoice not found");
        }

        invoiceRepository.deleteById(id);
=======
    private String generateInvoiceNumber() {
        long count = invoiceRepository.count() + 1;
        String numberStr;
        do {
            numberStr = String.format("INV-%d-%06d", Year.now().getValue(), count++);
        } while (invoiceRepository.existsByInvoiceNumber(numberStr));
        return numberStr;
>>>>>>> Stashed changes
    }

    private InvoiceResponseDTO mapToResponse(Invoice invoice) {

        List<InvoiceItemResponseDTO> items =
                invoice.getItems()
                        .stream()
                        .map(item ->
                                InvoiceItemResponseDTO.builder()
                                        .invoiceItemId(
                                                item.getInvoiceItemId())
                                        .itemType(
                                                item.getItemType())
                                        .productId(
                                                item.getProductId())
                                        .serviceId(
                                                item.getServiceId())
                                        .description(
                                                item.getDescription())
                                        .unit(
                                                item.getUnit())
                                        .quantity(
                                                item.getQuantity())
                                        .rate(
                                                item.getRate())
                                        .amount(
                                                item.getAmount())
                                        .build()
                        )
                        .toList();

        return InvoiceResponseDTO.builder()
                .invoiceId(invoice.getInvoiceId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .customerId(invoice.getCustomerId())
                .vehicleId(invoice.getVehicleId())
                .paymentMethod(invoice.getPaymentMethod())
                .invoiceDate(invoice.getInvoiceDate())
                .subtotal(invoice.getSubtotal())
                .taxAmount(invoice.getTaxAmount())
                .grandTotal(invoice.getGrandTotal())
                .items(items)
                .build();
    }

    /*
     * TEMPORARY METHODS
     *
     * Replace these with ProductRepository
     * and CarServiceRepository calls.
     */

    private BigDecimal getServicePrice(String serviceId) {

        if ("1".equals(serviceId) || "CS-0001".equalsIgnoreCase(serviceId)) {
            return carServiceRepository.findById(serviceId)
                    .map(CarService::getPrice)
                    .orElse(new BigDecimal("500.00"));
        }

        if ("2".equals(serviceId) || "CS-0002".equalsIgnoreCase(serviceId)) {
            return carServiceRepository.findById(serviceId)
                    .map(CarService::getPrice)
                    .orElse(new BigDecimal("2500.00"));
        }

        if ("3".equals(serviceId) || "CS-0003".equalsIgnoreCase(serviceId)) {
            return carServiceRepository.findById(serviceId)
                    .map(CarService::getPrice)
                    .orElse(new BigDecimal("800.00"));
        }

        return carServiceRepository.findById(serviceId)
                .map(CarService::getPrice)
                .orElseGet(() -> carServiceRepository.findByServiceCode(serviceId)
                        .map(CarService::getPrice)
                        .orElseThrow(() -> new RuntimeException(
                                "Service not found: " + serviceId
                        )));
    }

    private String getServiceDescription(String serviceId) {

        if ("1".equals(serviceId) || "CS-0001".equalsIgnoreCase(serviceId)) {
            return carServiceRepository.findById(serviceId)
                    .map(CarService::getServiceName)
                    .orElse("Premium Car Wash");
        }

        if ("2".equals(serviceId) || "CS-0002".equalsIgnoreCase(serviceId)) {
            return carServiceRepository.findById(serviceId)
                    .map(CarService::getServiceName)
                    .orElse("Premium Auto Detailing");
        }

        if ("3".equals(serviceId) || "CS-0003".equalsIgnoreCase(serviceId)) {
            return carServiceRepository.findById(serviceId)
                    .map(CarService::getServiceName)
                    .orElse("Wheel Alignment & Balancing");
        }

        return carServiceRepository.findById(serviceId)
                .map(CarService::getServiceName)
                .orElseGet(() -> carServiceRepository.findByServiceCode(serviceId)
                        .map(CarService::getServiceName)
                        .orElseThrow(() -> new RuntimeException(
                                "Service not found: " + serviceId
                        )));
    }

    private BigDecimal getProductPrice(String productId) {

        if ("5".equals(productId)) {
            return new BigDecimal("4500.00");
        }

        return productRepository.findById(productId)
                .map(p -> p.getSellingPrice() != null ? p.getSellingPrice() : new BigDecimal("100.00"))
                .orElseGet(() -> productRepository.findBySku(productId)
                        .map(p -> p.getSellingPrice() != null ? p.getSellingPrice() : new BigDecimal("100.00"))
                        .orElseGet(() -> productRepository.findByBarcode(productId)
                                .map(p -> p.getSellingPrice() != null ? p.getSellingPrice() : new BigDecimal("100.00"))
                                .orElse(new BigDecimal("1500.00"))));
    }

    private String getProductDescription(String productId) {

        if ("5".equals(productId)) {
            return "MRF Tyre";
        }

        return productRepository.findById(productId)
                .map(Product::getProductName)
                .orElseGet(() -> productRepository.findBySku(productId)
                        .map(Product::getProductName)
                        .orElseGet(() -> productRepository.findByBarcode(productId)
                                .map(Product::getProductName)
                                .orElse("Product (" + productId + ")")));
    }
}