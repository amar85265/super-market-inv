package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.dto.CarServiceRequestDTO;
import com.example.InventoryManagementSystem.dto.CarServiceResponseDTO;
import com.example.InventoryManagementSystem.service.CarServiceService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car-services")
@RequiredArgsConstructor
public class CarServiceController {

    private final CarServiceService carServiceService;

    @PostMapping
    public ResponseEntity<CarServiceResponseDTO> createService(
            @Valid @RequestBody CarServiceRequestDTO request
    ) {

        CarServiceResponseDTO response =
                carServiceService.createService(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<CarServiceResponseDTO>> getAllServices() {

        return ResponseEntity.ok(
                carServiceService.getAllServices()
        );
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<CarServiceResponseDTO> getServiceById(
            @PathVariable String serviceId
    ) {

        return ResponseEntity.ok(
                carServiceService.getServiceById(serviceId)
        );
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<CarServiceResponseDTO> updateService(
            @PathVariable String serviceId,
            @Valid @RequestBody CarServiceRequestDTO request
    ) {

        return ResponseEntity.ok(
                carServiceService.updateService(serviceId, request)
        );
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<String> deleteService(
            @PathVariable String serviceId
    ) {

        carServiceService.deleteService(serviceId);

        return ResponseEntity.ok(
                "Car service deleted successfully"
        );
    }
}