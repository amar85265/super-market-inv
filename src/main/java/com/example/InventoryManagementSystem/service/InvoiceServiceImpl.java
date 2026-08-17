package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.BillingCounterRepository;
import com.example.InventoryManagementSystem.Repository.CustomerRepository;
import com.example.InventoryManagementSystem.Repository.InvoiceRepository;
import com.example.InventoryManagementSystem.dto.InvoiceRequestDto;
import com.example.InventoryManagementSystem.dto.InvoiceResponseDto;
import com.example.InventoryManagementSystem.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BillingCounterRepository billingCounterRepository;

    @Override
    public InvoiceResponseDto createInvoice(InvoiceRequestDto dto) {

        if (!customerRepository.existsById(dto.getCustomerId())) {
            throw new RuntimeException("Customer not found");
        }

        if (!billingCounterRepository.existsById(dto.getCounterId())) {
            throw new RuntimeException("Billing Counter not found");
        }

        Invoice invoice = new Invoice();

        invoice.setCustomerId(dto.getCustomerId());
        invoice.setCounterId(dto.getCounterId());

        invoice.setSubtotal(BigDecimal.ZERO);
        invoice.setDiscountAmount(BigDecimal.ZERO);
        invoice.setTaxAmount(BigDecimal.ZERO);
        invoice.setGrandTotal(BigDecimal.ZERO);

        invoice.setPaidAmount(dto.getPaidAmount());
        invoice.setBalanceAmount(dto.getPaidAmount());

        invoice.setPaymentMethod(dto.getPaymentMethod());

        invoice.setPaymentStatus("PENDING");

        invoice.setCreatedBy(dto.getCreatedBy());

        invoice.setCreatedAt(OffsetDateTime.now());

        Invoice savedInvoice = invoiceRepository.save(invoice);

        String invoiceNumber =
                "INV-" +
                        Year.now().getValue() +
                        "-" +
                        savedInvoice.getInvoiceId();

        savedInvoice.setInvoiceNumber(invoiceNumber);

        savedInvoice = invoiceRepository.save(savedInvoice);

        return mapToResponse(savedInvoice);
    }

    @Override
    public InvoiceResponseDto getInvoiceById(String id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Invoice not found"));

        return mapToResponse(invoice);
    }

    @Override
    public List<InvoiceResponseDto> getAllInvoices() {

        return invoiceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceResponseDto updateInvoice(String invoiceId,
                                             InvoiceRequestDto dto) {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() ->
                        new RuntimeException("Invoice not found"));

        if (!customerRepository.existsById(dto.getCustomerId())) {
            throw new RuntimeException("Customer not found");
        }

        if (!billingCounterRepository.existsById(dto.getCounterId())) {
            throw new RuntimeException("Billing Counter not found");
        }

        invoice.setCustomerId(dto.getCustomerId());
        invoice.setCounterId(dto.getCounterId());
        invoice.setPaidAmount(dto.getPaidAmount());
        invoice.setPaymentMethod(dto.getPaymentMethod());
        invoice.setCreatedBy(dto.getCreatedBy());

        // Balance Amount
        invoice.setBalanceAmount(
                invoice.getGrandTotal().subtract(invoice.getPaidAmount())
        );

        // Payment Status
        if (invoice.getPaidAmount().compareTo(invoice.getGrandTotal()) >= 0) {
            invoice.setPaymentStatus("PAID");
        } else if (invoice.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
            invoice.setPaymentStatus("PARTIAL");
        } else {
            invoice.setPaymentStatus("PENDING");
        }

        Invoice updated = invoiceRepository.save(invoice);

        return mapToResponse(updated);
    }

    @Override
    public void deleteInvoice(String id) {

        if (!invoiceRepository.existsById(id)) {
            throw new RuntimeException("Invoice not found");
        }

        invoiceRepository.deleteById(id);
    }

    private InvoiceResponseDto mapToResponse(Invoice invoice) {

        InvoiceResponseDto dto = new InvoiceResponseDto();

        dto.setInvoiceId(invoice.getInvoiceId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setCustomerId(invoice.getCustomerId());
        dto.setCounterId(invoice.getCounterId());
        dto.setSubtotal(invoice.getSubtotal());
        dto.setDiscountAmount(invoice.getDiscountAmount());
        dto.setTaxAmount(invoice.getTaxAmount());
        dto.setGrandTotal(invoice.getGrandTotal());
        dto.setPaidAmount(invoice.getPaidAmount());
        dto.setBalanceAmount(invoice.getBalanceAmount());
        dto.setPaymentMethod(invoice.getPaymentMethod());
        dto.setPaymentStatus(invoice.getPaymentStatus());
        dto.setCreatedBy(invoice.getCreatedBy());
        dto.setCreatedAt(invoice.getCreatedAt());

        return dto;
    }
}