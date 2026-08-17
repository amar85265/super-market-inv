package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.SettingRequest;
import com.example.InventoryManagementSystem.dto.SettingResponse;

import java.util.List;

public interface SettingService {

    SettingResponse createSetting(SettingRequest request);

    SettingResponse updateSetting(String id, SettingRequest request);

    SettingResponse getSettingById(String id);

    List<SettingResponse> getAllSettings();

    void deleteSetting(String id);
}