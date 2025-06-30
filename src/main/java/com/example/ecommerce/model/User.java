package com.example.ecommerce.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String email;
    private String password;
    private String fullname;
    private String phoneNo;
    private String address;
    private boolean isVerified;

    @ManyToOne
    private Role role;
}
