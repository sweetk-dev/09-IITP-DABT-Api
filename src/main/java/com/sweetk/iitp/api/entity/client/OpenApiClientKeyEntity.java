package com.sweetk.iitp.api.entity.client;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "open_api_client_key")
@Getter
@Setter
public class OpenApiClientKeyEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "api_cli_id", nullable = false)
    private Integer apiCliId;
    
    @Column(name = "api_key", length = 60, nullable = false, unique = true)
    private String apiKey;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
    
    @Column(name = "key_active_at")
    private OffsetDateTime keyActiveAt;
    
    @Column(name = "latest_acc_at")
    private OffsetDateTime latestAccAt;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    
    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_cli_id", insertable = false, updatable = false)
    private OpenApiClientEntity openApiClient;
} 