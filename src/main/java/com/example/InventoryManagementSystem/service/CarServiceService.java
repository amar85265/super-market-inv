package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.CarServiceRequestDTO;
import com.example.InventoryManagementSystem.dto.CarServiceResponseDTO;

import java.util.List;

public interface CarServiceService {

    CarServiceResponseDTO createService(CarServiceRequestDTO request);

    List<CarServiceResponseDTO> getAllServices();

    CarServiceResponseDTO getServiceById(String serviceId);

    CarServiceResponseDTO updateService(
            String serviceId,
            CarServiceRequestDTO request
    );

    void deleteService(String serviceId);
}