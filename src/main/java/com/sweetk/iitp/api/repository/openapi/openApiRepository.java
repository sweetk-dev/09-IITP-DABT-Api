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

} 