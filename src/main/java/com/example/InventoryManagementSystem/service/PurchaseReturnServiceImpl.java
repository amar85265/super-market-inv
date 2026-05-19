package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.PurchaseReturnRequestDTO;
import com.example.InventoryManagementSystem.dto.PurchaseReturnResponseDTO;
import com.example.InventoryManagementSystem.model.PurchaseReturn;
import com.example.InventoryManagementSystem.Repository.PurchaseReturnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseReturnServiceImpl implements PurchaseReturnService {

    private final PurchaseReturnRepository repository;

    @Override
    public PurchaseReturnResponseDTO createPurchaseReturn(
            PurchaseReturnRequestDTO requestDTO) {

        PurchaseReturn entity = PurchaseReturn.builder()
                .purchaseId(requestDTO.getPurchaseId())
                .supplierId(requestDTO.getSupplierId())
                .returnDate(LocalDateTime.now())
                .totalAmount(requestDTO.getTotalAmount())
                .notes(requestDTO.getNotes())
                .build();

        PurchaseReturn saved = repository.save(entity);

        return mapToResponse(saved);
    }

    @Override
    public PurchaseReturnResponseDTO getPurchaseReturnById(Integer id) {

        PurchaseReturn entity = repository.findById(id).orElse(null);

        if (entity == null) {
            return null;
        }

        return mapToResponse(entity);
    }

    @Override
    public List<PurchaseReturnResponseDTO> getAllPurchaseReturns() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseReturnResponseDTO updatePurchaseReturn(
            Integer id,
            PurchaseReturnRequestDTO requestDTO) {

        PurchaseReturn entity = repository.findById(id).orElse(null);

        if (entity == null) {
            return null;
        }

        entity.setPurchaseId(requestDTO.getPurchaseId());
        entity.setSupplierId(requestDTO.getSupplierId());
        entity.setTotalAmount(requestDTO.getTotalAmount());
        entity.setNotes(requestDTO.getNotes());

        PurchaseReturn updated = repository.save(entity);

        return mapToResponse(updated);
    }

    @Override
    public void deletePurchaseReturn(Integer id) {

        PurchaseReturn entity = repository.findById(id).orElse(null);

        if (entity != null) {
            repository.delete(entity);
        }
    }

    private PurchaseReturnResponseDTO mapToResponse(
            PurchaseReturn entity) {

        return PurchaseReturnResponseDTO.builder()
                .purchaseReturnId(entity.getPurchaseReturnId())
                .purchaseId(entity.getPurchaseId())
                .supplierId(entity.getSupplierId())
                .returnDate(entity.getReturnDate())
                .totalAmount(entity.getTotalAmount())
                .notes(entity.getNotes())
                .build();
    }
}