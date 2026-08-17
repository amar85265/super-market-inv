package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.model.Role;

import java.util.List;

public interface RoleService {

    Role createRole(Role role);

    List<Role> getAllRoles();

    Role getRoleById(String id);

    Role updateRole(String id, Role role);

    void deleteRole(String id);
}