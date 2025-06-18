package com.sweetk.iitp.api.repository.client;

import com.sweetk.iitp.api.entity.client.OpenApiClientEntity;
import com.sweetk.iitp.api.entity.client.OpenApiClientKeyEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    
    // Client operations
    Optional<OpenApiClientEntity> findByClientId(String clientId);
    Optional<OpenApiClientEntity> findByClientIdAndIsDeletedFalse(String clientId);
    boolean existsByClientId(String clientId);
    boolean existsByClientIdAndIsDeletedFalse(String clientId);
    
    // API Key operations
    Optional<OpenApiClientKeyEntity> findByApiKey(String apiKey);
    Optional<OpenApiClientKeyEntity> findByApiKeyAndIsActiveTrueAndIsDeletedFalse(String apiKey);
    List<OpenApiClientKeyEntity> findByApiCliIdAndIsActiveTrueAndIsDeletedFalse(Integer apiCliId);
    boolean existsByApiKey(String apiKey);
    boolean existsByApiKeyAndIsActiveTrueAndIsDeletedFalse(String apiKey);
    
    // Combined operations for authentication
    Optional<OpenApiClientEntity> findClientByApiKey(String apiKey);
    Optional<OpenApiClientEntity> findActiveClientByApiKey(String apiKey);
    
    // Update operations
    void updateLatestAccessTime(Integer keyId, OffsetDateTime accessTime);
    void updateLatestLoginTime(Integer clientId, OffsetDateTime loginTime);
    void updateLatestKeyCreatedTime(Integer clientId, OffsetDateTime keyCreatedTime);
    
    // Key management
    void deactivateAllKeysByClientId(Integer apiCliId);
    OpenApiClientKeyEntity saveApiKey(OpenApiClientKeyEntity apiKey);
    OpenApiClientEntity saveClient(OpenApiClientEntity client);
} 