package com.example.ecommerce.config;

import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OwnerSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Cek apakah sudah ada user OWNER
        boolean ownerExists = userRepository.findByEmail("owner@olshop.com").isPresent();
        if (ownerExists) return;

        // Cari role OWNER, jika belum ada → buat
        Role ownerRole = roleRepository.findByRoleName("OWNER")
                .orElseGet(() -> roleRepository.save(new Role(null, "OWNER")));

        // Buat user OWNER
        User owner = User.builder()
                .username("owner")
                .email("owner@olshop.com")
                .password(passwordEncoder.encode("owner123")) // password default
                .fullname("Pemilik Toko")
                .phoneNo("08123456789")
                .address("Alamat Owner")
                .isVerified(true) // ✅ Tidak perlu verifikasi email
                .role(ownerRole)
                .build();

        userRepository.save(owner);
        System.out.println("✅ Seeded OWNER account: owner@olshop.com / owner123");
    }
}
