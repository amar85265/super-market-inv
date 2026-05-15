package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.model.Role;

import java.util.List;

public interface RoleService {

    Role createRole(Role role);

    List<Role> getAllRoles();

    Role getRoleById(Integer id);

    Role updateRole(Integer id, Role role);

    void deleteRole(Integer id);
}