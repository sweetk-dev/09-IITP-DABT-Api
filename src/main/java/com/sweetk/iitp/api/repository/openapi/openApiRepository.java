package com.sweetk.iitp.api.repository.openapi;

import com.sweetk.iitp.api.entity.openapi.OpenApiAuthKeyEntity;
import com.sweetk.iitp.api.entity.openapi.OpenApiUserEntity;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface openApiRepository {


    //===============================================================
    //Combined operations for authenti`cation (api openapi)
    //===============================================================

    // Update operations
    void updateLatestLoginTime(OpenApiUserEntity client, OffsetDateTime loginTime);
    void updateLatestAccessTime(OpenApiAuthKeyEntity key, OffsetDateTime accessTime);




    //===============================================================
    //Api Client operations
    //===============================================================
    //find api openapi
    Optional<OpenApiUserEntity> findClientByUserId (Long userId);
    Optional<OpenApiUserEntity> findClientByUserIdIncludingDeleted(Long userId);

    //upate api openapi
    void updateClientDeleteStatus(OpenApiUserEntity client, String deletedBy);


    //===============================================================
    // API Key operations
    //===============================================================
    //find api key
    Optional<OpenApiAuthKeyEntity> findActiveKeyByApiKey(String apiKey);


    //update api key


    /*  =========================== temp
    // Client operations
    Optional<OpenApiUserEntity> findByClientId(String clientId);
    Optional<OpenApiUserEntity> findByClientIdAndIsDeletedFalse(String clientId);
    Optional<OpenApiUserEntity> findActiveByClientId(String clientId);
    boolean existsByClientId(String clientId);
    boolean existsByClientIdAndIsDeletedFalse(String clientId);
    boolean existsAciveByClientId(String clientId);


    // API Key operations
    Optional<OpenApiAuthKeyEntity> findByApiKey(String apiKey);
    Optional<OpenApiAuthKeyEntity> findActiveByApiKeyAndIsDeletedFalse(String apiKey);
    Optional<OpenApiAuthKeyEntity> findByApiKeyAndActiveTrueAndIsDeletedFalse(String apiKey);
    List<OpenApiAuthKeyEntity> findByApiCliIdAndIsActiveTrueAndIsDeletedFalse(Integer apiCliId);
    boolean existsByApiKey(String apiKey);
    boolean existsByApiKeyAndIsActiveTrueAndIsDeletedFalse(String apiKey);
    
    // Combined operations for authentication
    Optional<OpenApiUserEntity> findClientByApiKey(String apiKey);
    Optional<OpenApiUserEntity> findActiveClientByApiKey(String apiKey);
    
    // Update operations
    void updateLatestKeyCreatedTime(Integer clientId, OffsetDateTime keyCreatedTime);
    
    // Key management
    void deactivateAllKeysByClientId(Integer apiCliId);
    OpenApiAuthKeyEntity saveApiKey(OpenApiAuthKeyEntity apiKey);
    OpenApiUserEntity saveClient(OpenApiUserEntity openapi);

     */
} 