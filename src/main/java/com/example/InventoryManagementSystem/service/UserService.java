package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.UserRequestDTO;
import com.example.InventoryManagementSystem.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Integer id);

    UserResponseDTO updateUser(Integer id, UserRequestDTO request);

    void deleteUser(Integer id);

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO request);

    void deleteUser(Long id);
}