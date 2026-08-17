package com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.model.Role;
import com.example.InventoryManagementSystem.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // CREATE ROLE
    @PostMapping
    public Role createRole(@Valid @RequestBody Role role) {
        return roleService.createRole(role);
    }

    // GET ALL ROLES
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // GET ROLE BY ID
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable String id) {
        return roleService.getRoleById(id);
    }

    // UPDATE ROLE
    @PutMapping("/{id}")
    public Role updateRole(
            @PathVariable String id,
            @RequestBody Role role) {

        return roleService.updateRole(id, role);
    }

    // DELETE ROLE
    @DeleteMapping("/{id}")
    public String deleteRole(@PathVariable String id) {

        roleService.deleteRole(id);
        return "Role deleted successfully";
    }
}