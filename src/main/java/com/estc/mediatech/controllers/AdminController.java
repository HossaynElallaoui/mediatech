package com.estc.mediatech.controllers;

import com.estc.mediatech.dto.CreateUserDto;
import com.estc.mediatech.dto.UserResponseDto;
import com.estc.mediatech.models.RoleEntity;
import com.estc.mediatech.models.UserEntity;
import com.estc.mediatech.repositories.RoleRepository;
import com.estc.mediatech.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Get all users (admin only)
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        try {
            List<UserEntity> users = userRepository.findAll();
            List<UserResponseDto> response = users.stream()
                    .map(user -> new UserResponseDto(
                            user.getId(),
                            user.getUsername(),
                            user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList())))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error getting all users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Get user by ID (admin only)
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id) {
        try {
            UserEntity user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            UserResponseDto response = new UserResponseDto(
                    user.getId(),
                    user.getUsername(),
                    user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error getting user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Create a new user (admin only)
    @PostMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto createUserDto) {
        try {
            if (userRepository.existsByUsername(createUserDto.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
            }

            UserEntity user = new UserEntity();
            user.setUsername(createUserDto.getUsername());
            user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));

            // Get the role
            String roleName = createUserDto.getRole() != null ? createUserDto.getRole() : "USER";
            RoleEntity role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            user.setRoles(Collections.singletonList(role));

            UserEntity savedUser = userRepository.save(user);
            UserResponseDto response = new UserResponseDto(
                    savedUser.getId(),
                    savedUser.getUsername(),
                    savedUser.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + e.getMessage());
        }
    }

    // Update user (admin only)
    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody CreateUserDto updateUserDto) {
        try {
            UserEntity user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (updateUserDto.getUsername() != null && !updateUserDto.getUsername().equals(user.getUsername())) {
                if (userRepository.existsByUsername(updateUserDto.getUsername())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
                }
                user.setUsername(updateUserDto.getUsername());
            }

            if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            }

            if (updateUserDto.getRole() != null) {
                RoleEntity role = roleRepository.findByName(updateUserDto.getRole())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + updateUserDto.getRole()));
                user.setRoles(Collections.singletonList(role));
            }

            UserEntity updatedUser = userRepository.save(user);
            UserResponseDto response = new UserResponseDto(
                    updatedUser.getId(),
                    updatedUser.getUsername(),
                    updatedUser.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error updating user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user: " + e.getMessage());
        }
    }

    // Delete user (admin only)
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        try {
            UserEntity user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Prevent admin from deleting themselves
            // You might want to add additional checks here

            userRepository.delete(user);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }

    // Get statistics (admin only)
    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getStats() {
        try {
            long userCount = userRepository.count();
            long roleCount = roleRepository.count();

            return ResponseEntity.ok(new java.util.HashMap<String, Object>() {
                {
                    put("totalUsers", userCount);
                    put("totalRoles", roleCount);
                }
            });
        } catch (Exception e) {
            System.out.println("Error getting stats: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting stats");
        }
    }
}
