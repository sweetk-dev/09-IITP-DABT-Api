package com.sweetk.iitp.api.repository.openapi;

import com.sweetk.iitp.api.constant.SysConstants;
import com.sweetk.iitp.api.entity.openapi.OpenApiAuthKeyEntity;
import com.sweetk.iitp.api.entity.openapi.OpenApiUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class openApiRepositoryImpl implements openApiRepository {
    
    private final OpenApiUserRepository openApiUserRepository;
    private final OpenApiAuthKeyRepository openApiAuthKeyRepository;



    /*****************************
     * API CLIENT AUTH
     *****************************/
    // Combined operations for authentication
    @Override
    public Optional<OpenApiUserEntity> findClientByUserIdIncludingDeleted(Long userId) {
        return openApiUserRepository.findByUserIdIncludingDeleted(userId);
    }


    @Override
    @Transactional
    public void updateLatestLoginTime(OpenApiUserEntity client, OffsetDateTime loginTime) {
        client.setLatestLoginAt(OffsetDateTime.now());
        openApiUserRepository.save(client);
    }


    @Override
    @Transactional
    public void updateLatestAccessTime(OpenApiAuthKeyEntity key, OffsetDateTime accessTime) {
        key.setLatestAccAt(accessTime);
        openApiAuthKeyRepository.save(key);
    }


    /*****************************
     * API CLIENT
     *****************************/

    // find api openapi
    public Optional<OpenApiUserEntity> findClientByUserId(Long userId) {
        return openApiUserRepository.findByUserId(userId);
    }


    // upate api openapi
    public void updateClientDeleteStatus(OpenApiUserEntity client, String deletedBy) {
        client.softDelete(deletedBy);
        openApiUserRepository.save(client);
    }



    /*****************************
     * API CLIENT KEY
     *****************************/

    // find api key
    public Optional<OpenApiAuthKeyEntity> findActiveKeyByApiKey(String apiKey) {
        return openApiAuthKeyRepository.findByAuthKeyAndActiveYn(apiKey, SysConstants.YN_Y);
    }




    //update api key





    /* ================== temp
    // Client operations
    @Override
    public Optional<OpenApiUserEntity> findByClientId(String clientId) {
        return openApiClientRepository.findByClientId(clientId);
    }
    
    @Override
    public Optional<OpenApiUserEntity> findByClientIdAndIsDeletedFalse(String clientId) {
        return openApiClientRepository.findByClientIdAndIsDeletedFalse(clientId);
    }

    @Override
    public Optional<OpenApiUserEntity> findActiveByClientId(String clientId) {
        return openApiClientRepository.findActiveByClientIdAndStatusAndIsDeletedFalse(clientId, DataStatusType.ACTIVE);
    }
    
    @Override
    public boolean existsByClientId(String clientId) {
        return openApiClientRepository.existsByClientId(clientId);
    }
    
    @Override
    public boolean existsByClientIdAndIsDeletedFalse(String clientId) {
        return openApiClientRepository.existsByClientIdAndIsDeletedFalse(clientId);
    }

    @Override
    public boolean existsAciveByClientId(String clientId) {
        return openApiClientRepository.existsActiveByClientIdAndStatusAndIsDeletedFalse(clientId, DataStatusType.ACTIVE);
    }





    // API Key operations
    @Override
    public Optional<OpenApiAuthKeyEntity> findByApiKey(String apiKey) {
        return openApiClientKeyRepository.findByApiKey(apiKey);
    }


    @Override
    public boolean existsByApiKey(String apiKey) {
        return openApiClientKeyRepository.existsByApiKey(apiKey);
    }
    
    @Override
    public boolean existsByApiKeyAndIsActiveTrueAndIsDeletedFalse(String apiKey) {
        return openApiClientKeyRepository.existsByApiKeyAndIsActiveTrueAndIsDeletedFalse(apiKey);
    }
    
    // Combined operations for authentication
    @Override
    public Optional<OpenApiUserEntity> findClientByApiKey(String apiKey) {
        return openApiClientKeyRepository.findByApiKey(apiKey)
                .map(key -> openApiClientRepository.findById(Long.valueOf(key.getUserId())))
                .orElse(Optional.empty());
    }
    

    
    // Update operations

    @Override
    @Transactional
    public void updateLatestKeyCreatedTime(Integer clientId, OffsetDateTime keyCreatedTime) {
        openApiClientRepository.findById(clientId).ifPresent(openapi -> {
            openapi.setLatestKeyCreatedAt(keyCreatedTime);
            openApiClientRepository.save(openapi);
        });
    }
    
    // Key management
    @Override
    @Transactional
    public void deactivateAllKeysByClientId(Integer apiCliId) {
        openApiClientKeyRepository.deactivateAllKeysByClientId(apiCliId);
    }
    
    @Override
    public OpenApiAuthKeyEntity saveApiKey(OpenApiAuthKeyEntity apiKey) {
        return openApiClientKeyRepository.save(apiKey);
    }
    
    @Override
    public OpenApiUserEntity saveClient(OpenApiUserEntity openapi) {
        return openApiClientRepository.save(openapi);
    }

     */
} 