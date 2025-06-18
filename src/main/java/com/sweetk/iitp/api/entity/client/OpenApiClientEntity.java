package com.sweetk.iitp.api.entity.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "open_api_client")
@Getter
@Setter
public class OpenApiClientEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_cli_id")
    private Integer apiCliId;
    
    @Column(name = "client_id", length = 40, nullable = false, unique = true)
    private String clientId;
    
    @Column(name = "password", length = 40, nullable = false)
    private String password;
    
    @Column(name = "client_name", length = 90, nullable = false)
    private String clientName;
    
    @Column(name = "description", length = 600)
    private String description;
    
    @Column(name = "note", length = 600)
    private String note;
    
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
    
    @Column(name = "latest_key_created_at")
    private OffsetDateTime latestKeyCreatedAt;
    
    @Column(name = "latest_login_at")
    private OffsetDateTime latestLoginAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
    
    @OneToMany(mappedBy = "openApiClient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OpenApiClientKeyEntity> apiKeys;
    
    public String getRole() {
        return "API_CLIENT";
    }
} 