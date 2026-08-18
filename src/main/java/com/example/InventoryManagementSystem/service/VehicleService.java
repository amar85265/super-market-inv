package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.VehicleRequestDTO;
import com.example.InventoryManagementSystem.dto.VehicleResponseDTO;

import java.util.List;

public interface VehicleService {

    VehicleResponseDTO createVehicle(
            VehicleRequestDTO requestDTO
    );

    List<VehicleResponseDTO> getAllVehicles();

    VehicleResponseDTO getVehicleById(String vehicleId);

    VehicleResponseDTO getVehicleByRegistration(
            String registrationNumber
    );

    List<VehicleResponseDTO> getVehiclesByCustomer(
            String customerId
    );

    List<VehicleResponseDTO> getActiveVehiclesByCustomer(
            String customerId
    );

    List<VehicleResponseDTO> searchVehicles(
            String registrationNumber
    );

    VehicleResponseDTO updateVehicle(
            String vehicleId,
            VehicleRequestDTO requestDTO
    );

    VehicleResponseDTO updateOdometer(
            String vehicleId,
            Integer currentOdometer
    );

    VehicleResponseDTO changeVehicleStatus(
            String vehicleId,
            Boolean active
    );

    void deleteVehicle(String vehicleId);
}