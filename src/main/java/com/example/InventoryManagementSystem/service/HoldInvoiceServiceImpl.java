package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.HoldInvoiceRepository;
import com.example.InventoryManagementSystem.dto.HoldInvoiceRequestDto;
import com.example.InventoryManagementSystem.dto.HoldInvoiceResponseDto;
import com.example.InventoryManagementSystem.model.HoldInvoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HoldInvoiceServiceImpl
        implements HoldInvoiceService {

    private final HoldInvoiceRepository holdInvoiceRepository;

    // CREATE
    @Override
    public HoldInvoiceResponseDto createHoldInvoice(
            HoldInvoiceRequestDto dto) {

        HoldInvoice holdInvoice =
                new HoldInvoice();

        holdInvoice.setData(dto.getData());

        HoldInvoice saved =
                holdInvoiceRepository.save(
                        holdInvoice);

        return mapToDto(saved);
    }

    // GET ALL
    @Override
    public List<HoldInvoiceResponseDto>
    getAllHoldInvoices() {

        return holdInvoiceRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    // GET BY ID
    @Override
    public HoldInvoiceResponseDto
    getHoldInvoiceById(Long id) {

        HoldInvoice holdInvoice =
                holdInvoiceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Hold invoice not found"));

        return mapToDto(holdInvoice);
    }

    @Override
    public HoldInvoiceResponseDto updateHoldInvoice(
            Long id,
            HoldInvoiceRequestDto dto) {

        HoldInvoice holdInvoice =
                holdInvoiceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Hold invoice not found"));

        holdInvoice.setData(dto.getData());

        HoldInvoice updated =
                holdInvoiceRepository.save(holdInvoice);

        return mapToDto(updated);
    }

    // DELETE
    @Override
    public void deleteHoldInvoice(Long id) {

        HoldInvoice holdInvoice =
                holdInvoiceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Hold invoice not found"));

        holdInvoiceRepository.delete(
                holdInvoice);
    }

    // MAP ENTITY TO DTO
    private HoldInvoiceResponseDto mapToDto(
            HoldInvoice holdInvoice) {

        return new HoldInvoiceResponseDto(
                holdInvoice.getHoldId(),
                holdInvoice.getData(),
                holdInvoice.getCreatedAt()
        );
    }
}