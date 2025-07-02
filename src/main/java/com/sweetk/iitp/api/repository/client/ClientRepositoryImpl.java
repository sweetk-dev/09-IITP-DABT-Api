package com.sweetk.iitp.api.repository.client;

import com.sweetk.iitp.api.constant.SysConstants;
import com.sweetk.iitp.api.entity.client.OpenApiClientEntity;
import com.sweetk.iitp.api.entity.client.OpenApiClientKeyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {
    
    private final OpenApiClientRepository openApiClientRepository;
    private final OpenApiClientKeyRepository openApiClientKeyRepository;



    /*****************************
     * API CLIENT AUTH
     *****************************/
    // Combined operations for authentication
    @Override
    public Optional<OpenApiClientEntity> findClientByApiCliIdIncludingDeleted(Integer apiCliId) {
        return openApiClientRepository.findByApiCliIdIncludingDeleted(apiCliId);
    }


    @Override
    @Transactional
    public void updateLatestLoginTime(OpenApiClientEntity client, OffsetDateTime loginTime) {
        client.setLatestLoginAt(OffsetDateTime.now());
        openApiClientRepository.save(client);
    }


    @Override
    @Transactional
    public void updateLatestAccessTime(OpenApiClientKeyEntity key, OffsetDateTime accessTime) {
        key.setLatestAccAt(accessTime);
        openApiClientKeyRepository.save(key);
    }


    /*****************************
     * API CLIENT
     *****************************/

    // find api client
    public Optional<OpenApiClientEntity> findClientByApiCliId(Integer apiCliId) {
        return openApiClientRepository.findByApiCliId(apiCliId);
    }


    // upate api client
    public void updateClientDeleteStatus(OpenApiClientEntity client, String deletedBy) {
        client.softDelete(deletedBy);
        openApiClientRepository.save(client);
    }



    /*****************************
     * API CLIENT KEY
     *****************************/

    // find api key
    public Optional<OpenApiClientKeyEntity> findActiveKeyByApiKey(String apiKey) {
        return openApiClientKeyRepository.findByApiKeyAndActiveYn(apiKey, SysConstants.YN_Y);
    }




    //update api key





    /* ================== temp
    // Client operations
    @Override
    public Optional<OpenApiClientEntity> findByClientId(String clientId) {
        return openApiClientRepository.findByClientId(clientId);
    }
    
    @Override
    public Optional<OpenApiClientEntity> findByClientIdAndIsDeletedFalse(String clientId) {
        return openApiClientRepository.findByClientIdAndIsDeletedFalse(clientId);
    }

    @Override
    public Optional<OpenApiClientEntity> findActiveByClientId(String clientId) {
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
    public Optional<OpenApiClientKeyEntity> findByApiKey(String apiKey) {
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
    public Optional<OpenApiClientEntity> findClientByApiKey(String apiKey) {
        return openApiClientKeyRepository.findByApiKey(apiKey)
                .map(key -> openApiClientRepository.findById(key.getApiCliId()))
                .orElse(Optional.empty());
    }
    

    
    // Update operations

    @Override
    @Transactional
    public void updateLatestKeyCreatedTime(Integer clientId, OffsetDateTime keyCreatedTime) {
        openApiClientRepository.findById(clientId).ifPresent(client -> {
            client.setLatestKeyCreatedAt(keyCreatedTime);
            openApiClientRepository.save(client);
        });
    }
    
    // Key management
    @Override
    @Transactional
    public void deactivateAllKeysByClientId(Integer apiCliId) {
        openApiClientKeyRepository.deactivateAllKeysByClientId(apiCliId);
    }
    
    @Override
    public OpenApiClientKeyEntity saveApiKey(OpenApiClientKeyEntity apiKey) {
        return openApiClientKeyRepository.save(apiKey);
    }
    
    @Override
    public OpenApiClientEntity saveClient(OpenApiClientEntity client) {
        return openApiClientRepository.save(client);
    }

     */
} 