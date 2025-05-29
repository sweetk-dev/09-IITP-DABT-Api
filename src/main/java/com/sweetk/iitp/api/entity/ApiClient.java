package com.sweetk.iitp.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "api_clients")
@Getter
@Setter
@NoArgsConstructor
public class ApiClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String clientId;

    @Column(nullable = false)
    private String apiKey;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private String role;
} 