package com.example.surest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.surest.entity.Role;
import com.example.surest.entity.User;
import com.example.surest.repository.RoleRepository;
import com.example.surest.repository.UserRepository;

import java.util.Optional;

@SpringBootApplication
@EnableCaching
public class SurestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurestApplication.class, args);
    }

    // Seed two users (admin and user) at startup if they do not exist.
    @Bean
    CommandLineRunner seed(RoleRepository roleRepo, UserRepository userRepo, BCryptPasswordEncoder encoder) {
        return args -> {
            Optional<Role> adminRole = roleRepo.findByName("ROLE_ADMIN");
            Optional<Role> userRole = roleRepo.findByName("ROLE_USER");

            if (adminRole.isPresent() && userRole.isPresent()) {
                if (!userRepo.existsByUsername("admin")) {
                    User admin = new User();
                    admin.setUsername("admin");
                    admin.setPasswordHash(encoder.encode("adminpass"));
                    admin.setRole(adminRole.get());
                    userRepo.save(admin);
                }
                if (!userRepo.existsByUsername("user")) {
                    User u = new User();
                    u.setUsername("user");
                    u.setPasswordHash(encoder.encode("userpass"));
                    u.setRole(userRole.get());
                    userRepo.save(u);
                }
            }
        };
    }
}
