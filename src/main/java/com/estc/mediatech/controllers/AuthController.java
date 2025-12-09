package com.estc.mediatech.controllers;

import com.estc.mediatech.dto.AuthResponseDto;
import com.estc.mediatech.dto.LoginDto;
import com.estc.mediatech.dto.RegisterDto;
import com.estc.mediatech.models.RoleEntity;
import com.estc.mediatech.models.UserEntity;
import com.estc.mediatech.repositories.RoleRepository;
import com.estc.mediatech.repositories.UserRepository;
import com.estc.mediatech.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            // Log the login attempt
            System.out.println("Login attempt for username: " + loginDto.getUsername());

            // Check if user exists
            if (!userRepository.existsByUsername(loginDto.getUsername())) {
                System.out.println("User not found: " + loginDto.getUsername());
                return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            System.out.println("Login successful for: " + loginDto.getUsername());
            return new ResponseEntity<AuthResponseDto>(new AuthResponseDto(token), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            System.out.println("Bad credentials for user: " + loginDto.getUsername());
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Login failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        try {
            System.out.println("Registration attempt for username: " + registerDto.getUsername());

            if (userRepository.existsByUsername(registerDto.getUsername())) {
                return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
            }

            UserEntity user = new UserEntity();
            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            RoleEntity roles = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException(
                            "Error: Role USER not found. Please run: INSERT INTO roles (name) VALUES ('USER');"));
            user.setRoles(Collections.singletonList(roles));

            userRepository.save(user);
            System.out.println("User registered successfully: " + registerDto.getUsername());

            return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Debug endpoint - remove in production
    @GetMapping("debug/users")
    public ResponseEntity<?> debugUsers() {
        try {
            long userCount = userRepository.count();
            long roleCount = roleRepository.count();
            return ResponseEntity.ok("Users: " + userCount + ", Roles: " + roleCount);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
