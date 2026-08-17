package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.UnitRequestDto;
import com.example.InventoryManagementSystem.dto.UnitResponseDto;

import java.util.List;

public interface UnitService {

    UnitResponseDto createUnit(
            UnitRequestDto dto);

    List<UnitResponseDto> getAllUnits();

    UnitResponseDto getUnitById(String id);

    UnitResponseDto updateUnit(
            String id,
            UnitRequestDto dto);

    void deleteUnit(String id);
}