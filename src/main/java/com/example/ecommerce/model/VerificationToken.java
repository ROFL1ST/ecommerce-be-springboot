package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class VerificationToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;
    private LocalDateTime expiryDate;

    @OneToOne
    private User user;
}