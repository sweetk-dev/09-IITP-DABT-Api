package com.sweetk.iitp.api.repository.client;

import com.sweetk.iitp.api.entity.client.OpenApiClientEntity;
import com.sweetk.iitp.api.entity.client.OpenApiClientKeyEntity;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface ClientRepository {

    //Api Client operations




    // API Key operations
    Optional<OpenApiClientKeyEntity> findActiveKeyByApiKey(String apiKey);




    //Combined operations for authentication (api client)
    Optional<OpenApiClientEntity> findActiveClientByApiKey(String apiKey);




    // Update operations
    void updateLatestLoginTime(Integer clientId, OffsetDateTime loginTime);
    void updateLatestAccessTime(Integer keyId, OffsetDateTime accessTime);


    // Key management



    /*
    // Client operations
    Optional<OpenApiClientEntity> findByClientId(String clientId);
    Optional<OpenApiClientEntity> findByClientIdAndIsDeletedFalse(String clientId);
    Optional<OpenApiClientEntity> findActiveByClientId(String clientId);
    boolean existsByClientId(String clientId);
    boolean existsByClientIdAndIsDeletedFalse(String clientId);
    boolean existsAciveByClientId(String clientId);


    // API Key operations
    Optional<OpenApiClientKeyEntity> findByApiKey(String apiKey);
    Optional<OpenApiClientKeyEntity> findActiveByApiKeyAndIsDeletedFalse(String apiKey);
    Optional<OpenApiClientKeyEntity> findByApiKeyAndActiveTrueAndIsDeletedFalse(String apiKey);
    List<OpenApiClientKeyEntity> findByApiCliIdAndIsActiveTrueAndIsDeletedFalse(Integer apiCliId);
    boolean existsByApiKey(String apiKey);
    boolean existsByApiKeyAndIsActiveTrueAndIsDeletedFalse(String apiKey);
    
    // Combined operations for authentication
    Optional<OpenApiClientEntity> findClientByApiKey(String apiKey);
    Optional<OpenApiClientEntity> findActiveClientByApiKey(String apiKey);
    
    // Update operations
    void updateLatestKeyCreatedTime(Integer clientId, OffsetDateTime keyCreatedTime);
    
    // Key management
    void deactivateAllKeysByClientId(Integer apiCliId);
    OpenApiClientKeyEntity saveApiKey(OpenApiClientKeyEntity apiKey);
    OpenApiClientEntity saveClient(OpenApiClientEntity client);

     */
} 