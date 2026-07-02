package com.example.InventoryManagementSystem.service;

<<<<<<< Updated upstream
import com.example.InventoryManagementSystem.Dto.UserRequestDTO;
import com.example.InventoryManagementSystem.Dto.UserResponseDTO;
import com.example.InventoryManagementSystem.model.Role;
=======
import com.example.InventoryManagementSystem.dto.UserRequestDTO;
import com.example.InventoryManagementSystem.dto.UserResponseDTO;
>>>>>>> Stashed changes
import com.example.InventoryManagementSystem.model.User;
import com.example.InventoryManagementSystem.Repository.UserRepository;
<<<<<<< Updated upstream
import com.example.InventoryManagementSystem.service.UserService;
=======

>>>>>>> Stashed changes
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {

        // Validate duplicate username
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException(
                    "Username already exists: " + request.getUsername());
        }

        // Validate duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException(
                    "Email already exists: " + request.getEmail());
        }

        User user = User.builder()
                .username(request.getUsername())  // THIS WAS MISSING
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    private UserResponseDTO mapToResponse(User user) {
        return UserResponseDTO.builder()
                .Id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .mobileNumber(user.getMobileNumber())
                .status(user.getStatus())
                .active(user.getActive())
                .createdAt(user.getCreatedAt() != null ?
                        user.getCreatedAt().toString() : null)
                .updatedAt(user.getUpdatedAt() != null ?
                        user.getUpdatedAt().toString() : null)
                .build();
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));
        return mapToResponse(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null
                && !request.getPassword().isEmpty()) {
            user.setPasswordHash(
                    passwordEncoder.encode(request.getPassword()));
        }

        return mapToResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));
        userRepository.delete(user);
    }
}