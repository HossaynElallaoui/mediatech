package com.estc.mediatech.config;

import com.estc.mediatech.models.RoleEntity;
import com.estc.mediatech.models.UserEntity;
import com.estc.mediatech.repositories.RoleRepository;
import com.estc.mediatech.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {
            System.out.println("Initializing database with default roles and admin user...");

            // Create ADMIN role if it doesn't exist
            RoleEntity adminRole;
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                adminRole = new RoleEntity();
                adminRole.setName("ADMIN");
                adminRole = roleRepository.save(adminRole);
                System.out.println("Created ADMIN role");
            } else {
                adminRole = roleRepository.findByName("ADMIN").get();
                System.out.println("ADMIN role already exists");
            }

            // Create USER role if it doesn't exist
            RoleEntity userRole;
            if (roleRepository.findByName("USER").isEmpty()) {
                userRole = new RoleEntity();
                userRole.setName("USER");
                userRole = roleRepository.save(userRole);
                System.out.println("Created USER role");
            } else {
                userRole = roleRepository.findByName("USER").get();
                System.out.println("USER role already exists");
            }

            // Create default admin user if it doesn't exist
            if (!userRepository.existsByUsername("admin")) {
                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // Default password
                admin.setRoles(Collections.singletonList(adminRole));
                userRepository.save(admin);
                System.out.println("Created default admin user (username: admin, password: admin123)");
            } else {
                System.out.println("Admin user already exists");
            }

            // Create a default regular user for testing
            if (!userRepository.existsByUsername("user")) {
                UserEntity user = new UserEntity();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123")); // Default password
                user.setRoles(Collections.singletonList(userRole));
                userRepository.save(user);
                System.out.println("Created default user account (username: user, password: user123)");
            } else {
                System.out.println("Default user account already exists");
            }

            System.out.println("Database initialization completed!");
            System.out.println("Total users: " + userRepository.count());
            System.out.println("Total roles: " + roleRepository.count());
        } catch (Exception e) {
            System.err.println("Error during database initialization: " + e.getMessage());
            e.printStackTrace();
            // Don't rethrow - allow the application to start even if initialization fails
        }
    }
}
