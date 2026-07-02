package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.Dto.UserRequestDTO;
import com.example.InventoryManagementSystem.Dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO request);

    void deleteUser(Long id);
}