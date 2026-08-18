package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.VehicleRequestDTO;
import com.example.InventoryManagementSystem.dto.VehicleResponseDTO;
import com.example.InventoryManagementSystem.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor

public class VehicleController {

    private final VehicleService vehicleService;


    // CREATE VEHICLE
    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(
            @Valid @RequestBody VehicleRequestDTO requestDTO) {

        VehicleResponseDTO response =
                vehicleService.createVehicle(requestDTO);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }


    // GET ALL VEHICLES
    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>>
    getAllVehicles() {

        return ResponseEntity.ok(
                vehicleService.getAllVehicles()
        );
    }


    // GET VEHICLE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO>
    getVehicleById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                vehicleService.getVehicleById(id)
        );
    }


    // GET VEHICLE BY REGISTRATION NUMBER
    @GetMapping("/registration/{registrationNumber}")
    public ResponseEntity<VehicleResponseDTO>
    getVehicleByRegistration(
            @PathVariable String registrationNumber) {

        return ResponseEntity.ok(
                vehicleService.getVehicleByRegistration(
                        registrationNumber
                )
        );
    }


    // GET VEHICLES BY CUSTOMER
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<VehicleResponseDTO>>
    getVehiclesByCustomer(
            @PathVariable String customerId) {

        return ResponseEntity.ok(
                vehicleService.getVehiclesByCustomer(
                        customerId
                )
        );
    }


    // GET ACTIVE VEHICLES BY CUSTOMER
    @GetMapping("/customer/{customerId}/active")
    public ResponseEntity<List<VehicleResponseDTO>>
    getActiveVehiclesByCustomer(
            @PathVariable String customerId) {

        return ResponseEntity.ok(
                vehicleService.getActiveVehiclesByCustomer(
                        customerId
                )
        );
    }


    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<VehicleResponseDTO>>
    searchVehicles(
            @RequestParam String registrationNumber) {

        return ResponseEntity.ok(
                vehicleService.searchVehicles(
                        registrationNumber
                )
        );
    }


    // UPDATE VEHICLE
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO>
    updateVehicle(
            @PathVariable String id,
            @Valid @RequestBody VehicleRequestDTO requestDTO) {

        return ResponseEntity.ok(
                vehicleService.updateVehicle(
                        id,
                        requestDTO
                )
        );
    }


    // UPDATE ODOMETER
    @PatchMapping("/{id}/odometer")
    public ResponseEntity<VehicleResponseDTO>
    updateOdometer(
            @PathVariable String id,
            @RequestParam Integer currentOdometer) {

        return ResponseEntity.ok(
                vehicleService.updateOdometer(
                        id,
                        currentOdometer
                )
        );
    }


    // ACTIVATE / DEACTIVATE VEHICLE
    @PatchMapping("/{id}/status")
    public ResponseEntity<VehicleResponseDTO>
    changeVehicleStatus(
            @PathVariable String id,
            @RequestParam Boolean active) {

        return ResponseEntity.ok(
                vehicleService.changeVehicleStatus(
                        id,
                        active
                )
        );
    }


    // SOFT DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable String id) {

        vehicleService.deleteVehicle(id);

        return ResponseEntity.noContent().build();
    }
}