package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Repository.VehicleRepository;
import com.example.InventoryManagementSystem.dto.VehicleRequestDTO;
import com.example.InventoryManagementSystem.dto.VehicleResponseDTO;
import com.example.InventoryManagementSystem.model.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;


    // CREATE VEHICLE
    @Override
    public VehicleResponseDTO createVehicle(
            VehicleRequestDTO requestDTO) {

        String registration =
                requestDTO.getRegistrationNumber()
                        .trim()
                        .toUpperCase();

        // Registration number must be unique
        if (vehicleRepository
                .existsByRegistrationNumberIgnoreCase(registration)) {

            throw new RuntimeException(
                    "Vehicle already exists with registration number: "
                            + registration
            );
        }

        // VIN must be unique if provided
        if (requestDTO.getVinNumber() != null
                && !requestDTO.getVinNumber().isBlank()
                && vehicleRepository.existsByVinNumberIgnoreCase(
                requestDTO.getVinNumber())) {

            throw new RuntimeException(
                    "Vehicle already exists with VIN number: "
                            + requestDTO.getVinNumber()
            );
        }

        Vehicle vehicle = Vehicle.builder()
                .customerId(requestDTO.getCustomerId())
                .registrationNumber(registration)
                .vinNumber(requestDTO.getVinNumber())
                .vehicleType(requestDTO.getVehicleType())
                .brand(requestDTO.getBrand())
                .model(requestDTO.getModel())
                .variant(requestDTO.getVariant())
                .manufacturingYear(requestDTO.getManufacturingYear())
                .color(requestDTO.getColor())
                .fuelType(requestDTO.getFuelType())
                .currentOdometer(requestDTO.getCurrentOdometer())
                .lastServiceOdometer(
                        requestDTO.getLastServiceOdometer()
                )
                .status(
                        requestDTO.getStatus() != null
                                ? requestDTO.getStatus()
                                : "ACTIVE"
                )
                .active(
                        requestDTO.getActive() != null
                                ? requestDTO.getActive()
                                : true
                )
                .build();

        Vehicle savedVehicle =
                vehicleRepository.save(vehicle);

        return convertToResponse(savedVehicle);
    }


    // GET ALL
    @Override
    public List<VehicleResponseDTO> getAllVehicles() {

        return vehicleRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // GET BY ID
    @Override
    public VehicleResponseDTO getVehicleById(
            String vehicleId) {

        Vehicle vehicle =
                vehicleRepository.findById(vehicleId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle not found with ID: "
                                                + vehicleId
                                ));

        return convertToResponse(vehicle);
    }


    // GET BY REGISTRATION NUMBER
    @Override
    public VehicleResponseDTO getVehicleByRegistration(
            String registrationNumber) {

        Vehicle vehicle =
                vehicleRepository
                        .findByRegistrationNumberIgnoreCase(
                                registrationNumber
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle not found with registration number: "
                                                + registrationNumber
                                ));

        return convertToResponse(vehicle);
    }


    // GET VEHICLES OF CUSTOMER
    @Override
    public List<VehicleResponseDTO> getVehiclesByCustomer(
            String customerId) {

        return vehicleRepository
                .findByCustomerId(customerId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // GET ACTIVE VEHICLES OF CUSTOMER
    @Override
    public List<VehicleResponseDTO> getActiveVehiclesByCustomer(
            String customerId) {

        return vehicleRepository
                .findByCustomerIdAndActiveTrue(customerId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // SEARCH
    @Override
    public List<VehicleResponseDTO> searchVehicles(
            String registrationNumber) {

        return vehicleRepository
                .findByRegistrationNumberContainingIgnoreCase(
                        registrationNumber
                )
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // UPDATE VEHICLE
    @Override
    public VehicleResponseDTO updateVehicle(
            String vehicleId,
            VehicleRequestDTO requestDTO) {

        Vehicle existingVehicle =
                vehicleRepository.findById(vehicleId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle not found with ID: "
                                                + vehicleId
                                ));


        String registration =
                requestDTO.getRegistrationNumber()
                        .trim()
                        .toUpperCase();


        // Prevent duplicate registration number
        if (!existingVehicle
                .getRegistrationNumber()
                .equalsIgnoreCase(registration)
                && vehicleRepository
                .existsByRegistrationNumberIgnoreCase(
                        registration)) {

            throw new RuntimeException(
                    "Another vehicle already has registration number: "
                            + registration
            );
        }


        existingVehicle.setCustomerId(
                requestDTO.getCustomerId()
        );

        existingVehicle.setRegistrationNumber(
                registration
        );

        existingVehicle.setVinNumber(
                requestDTO.getVinNumber()
        );

        existingVehicle.setVehicleType(
                requestDTO.getVehicleType()
        );

        existingVehicle.setBrand(
                requestDTO.getBrand()
        );

        existingVehicle.setModel(
                requestDTO.getModel()
        );

        existingVehicle.setVariant(
                requestDTO.getVariant()
        );

        existingVehicle.setManufacturingYear(
                requestDTO.getManufacturingYear()
        );

        existingVehicle.setColor(
                requestDTO.getColor()
        );

        existingVehicle.setFuelType(
                requestDTO.getFuelType()
        );

        existingVehicle.setCurrentOdometer(
                requestDTO.getCurrentOdometer()
        );

        existingVehicle.setLastServiceOdometer(
                requestDTO.getLastServiceOdometer()
        );

        if (requestDTO.getStatus() != null) {
            existingVehicle.setStatus(
                    requestDTO.getStatus()
            );
        }

        if (requestDTO.getActive() != null) {
            existingVehicle.setActive(
                    requestDTO.getActive()
            );
        }


        Vehicle updatedVehicle =
                vehicleRepository.save(existingVehicle);

        return convertToResponse(updatedVehicle);
    }


    // UPDATE ODOMETER
    @Override
    public VehicleResponseDTO updateOdometer(
            String vehicleId,
            Integer currentOdometer) {

        Vehicle vehicle =
                vehicleRepository.findById(vehicleId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle not found with ID: "
                                                + vehicleId
                                ));


        // Odometer should never go backwards
        if (vehicle.getCurrentOdometer() != null
                && currentOdometer
                < vehicle.getCurrentOdometer()) {

            throw new RuntimeException(
                    "New odometer reading cannot be less than "
                            + "current odometer reading"
            );
        }


        vehicle.setCurrentOdometer(
                currentOdometer
        );

        Vehicle updatedVehicle =
                vehicleRepository.save(vehicle);

        return convertToResponse(updatedVehicle);
    }


    // ACTIVATE / DEACTIVATE
    @Override
    public VehicleResponseDTO changeVehicleStatus(
            String vehicleId,
            Boolean active) {

        Vehicle vehicle =
                vehicleRepository.findById(vehicleId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle not found with ID: "
                                                + vehicleId
                                ));

        vehicle.setActive(active);

        vehicle.setStatus(
                active ? "ACTIVE" : "INACTIVE"
        );

        Vehicle updatedVehicle =
                vehicleRepository.save(vehicle);

        return convertToResponse(updatedVehicle);
    }


    // SOFT DELETE
    @Override
    public void deleteVehicle(String vehicleId) {

        Vehicle vehicle =
                vehicleRepository.findById(vehicleId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle not found with ID: "
                                                + vehicleId
                                ));

        // Don't permanently delete vehicle.
        // Keep it for service history.
        vehicle.setActive(false);
        vehicle.setStatus("INACTIVE");

        vehicleRepository.save(vehicle);
    }


    // ENTITY → RESPONSE DTO
    private VehicleResponseDTO convertToResponse(
            Vehicle vehicle) {

        return VehicleResponseDTO.builder()
                .vehicleId(vehicle.getVehicleId())
                .customerId(vehicle.getCustomerId())
                .registrationNumber(
                        vehicle.getRegistrationNumber()
                )
                .vinNumber(vehicle.getVinNumber())
                .vehicleType(vehicle.getVehicleType())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .variant(vehicle.getVariant())
                .manufacturingYear(
                        vehicle.getManufacturingYear()
                )
                .color(vehicle.getColor())
                .fuelType(vehicle.getFuelType())
                .currentOdometer(
                        vehicle.getCurrentOdometer()
                )
                .lastServiceOdometer(
                        vehicle.getLastServiceOdometer()
                )
                .status(vehicle.getStatus())
                .active(vehicle.getActive())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }
}