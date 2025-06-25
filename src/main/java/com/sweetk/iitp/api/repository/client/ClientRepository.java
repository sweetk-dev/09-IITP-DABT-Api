package com.sweetk.iitp.api.repository.client;

import com.sweetk.iitp.api.entity.client.OpenApiClientEntity;
import com.sweetk.iitp.api.entity.client.OpenApiClientKeyEntity;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface ClientRepository {


    //===============================================================
    //Combined operations for authenti`cation (api client)
    //===============================================================

    // Update operations
    void updateLatestLoginTime(OpenApiClientEntity client, OffsetDateTime loginTime);
    void updateLatestAccessTime(OpenApiClientKeyEntity key, OffsetDateTime accessTime);




    //===============================================================
    //Api Client operations
    //===============================================================
    //find api client
    Optional<OpenApiClientEntity> findClientByApiCliId (Integer apiCliId);
    Optional<OpenApiClientEntity> findClientByApiCliIdIncludingDeleted(Integer apiCliId);

    //upate api client
    void updateClientDeleteStatus(OpenApiClientEntity client, String deletedBy);


    //===============================================================
    // API Key operations
    //===============================================================
    //find api key
    Optional<OpenApiClientKeyEntity> findActiveKeyByApiKey(String apiKey);


    //update api key


    /*  =========================== temp
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