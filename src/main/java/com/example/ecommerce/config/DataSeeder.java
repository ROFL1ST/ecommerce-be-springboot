package com.example.ecommerce.config;

import com.example.ecommerce.model.Role;
import com.example.ecommerce.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        seedRoles();
    }

    private void seedRoles() {
        if (roleRepository.findByRoleName("CUSTOMER").isEmpty()) {
            roleRepository.save(new Role(null, "CUSTOMER"));
        }

        if (roleRepository.findByRoleName("ADMIN").isEmpty()) {
            roleRepository.save(new Role(null, "ADMIN"));
        }

        if (roleRepository.findByRoleName("OWNER").isEmpty()) {
            roleRepository.save(new Role(null, "OWNER"));
        }

        System.out.println("✅ Role seeding complete");
    }
}
