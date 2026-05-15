package


































































































com.example.InventoryManagementSystem.controllor;

import com.example.InventoryManagementSystem.Dto.UserRequestDTO;
import com.example.InventoryManagementSystem.Dto.UserResponseDTO;
import com.example.InventoryManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // CREATE USER
    @PostMapping
    public UserResponseDTO createUser(
            @RequestBody UserRequestDTO request) {

        return userService.createUser(request);
    }

    // GET ALL USERS
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {

        return userService.getAllUsers();
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(
            @PathVariable Integer id) {

        return userService.getUserById(id);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Integer id,
            @RequestBody UserRequestDTO request) {

        return userService.updateUser(id, request);
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public String deleteUser(
            @PathVariable Integer id) {

        userService.deleteUser(id);

        return "User deleted successfully";
    }
}