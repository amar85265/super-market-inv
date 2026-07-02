<<<<<<< Updated upstream
package


































































































com.example.InventoryManagementSystem.controllor;
=======
package com.example.InventoryManagementSystem.controllor;
>>>>>>> Stashed changes

import com.example.InventoryManagementSystem.Dto.UserRequestDTO;
import com.example.InventoryManagementSystem.Dto.UserResponseDTO;
import com.example.InventoryManagementSystem.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
//ssfsasa
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody UserRequestDTO request) {

        return ResponseEntity.ok(
                userService.createUser(request));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully");
    }
}