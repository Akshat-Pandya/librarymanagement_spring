package com.pandastudios.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.pandastudios.librarymanagement.entity.enums.Role;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt=LocalDateTime.now();

}

