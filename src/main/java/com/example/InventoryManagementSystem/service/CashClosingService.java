package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.CashClosingRequestDto;
import com.example.InventoryManagementSystem.dto.CashClosingResponseDto;

import java.util.List;

public interface CashClosingService {

    CashClosingResponseDto createCashClosing(
            CashClosingRequestDto dto);

    List<CashClosingResponseDto> getAllCashClosings();

    CashClosingResponseDto getCashClosingById(
            String id);

    CashClosingResponseDto updateCashClosing(
            String id,
            CashClosingRequestDto dto);

    void deleteCashClosing(String id);
}
