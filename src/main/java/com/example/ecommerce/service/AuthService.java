package com.example.ecommerce.service;

import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.VerificationToken;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.repository.VerificationTokenRepository;
import com.example.ecommerce.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request) {
        Role role = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User(request, role);
    }

    public void registerAdmin(RegisterRequest request) {
        Role role = roleRepository.findByRoleName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User(request, role);
    }

    private void User(RegisterRequest request, Role role) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullname(request.getFullname())
                .phoneNo(request.getPhoneNo())
                .address(request.getAddress())
                .isVerified(false)
                .role(role)
                .build();

        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(null, token, LocalDateTime.now().plusHours(24), user);
        tokenRepository.save(verificationToken);

        String link = "http://localhost:8080/api/auth/verify?token=" + token;
        emailService.sendVerificationEmail(user.getEmail(), link);
    }

    public boolean verifyToken(String token) {
        Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            VerificationToken vToken = optionalToken.get();
            if (vToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                User user = vToken.getUser();
                user.setVerified(true);
                userRepository.save(user);
                tokenRepository.delete(vToken);
                return true;
            }
        }
        return false;
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email tidak ditemukan"));

        if (!user.isVerified()) {
            throw new RuntimeException("Akun belum diverifikasi.");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Password salah.");
        }

        return jwtUtil.generateToken(email);
    }

    public User getUserFromPrincipal(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
    }
}