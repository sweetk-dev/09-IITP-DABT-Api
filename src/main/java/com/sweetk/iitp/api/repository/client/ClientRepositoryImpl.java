package com.sweetk.iitp.api.repository.client;

import com.sweetk.iitp.api.entity.client.OpenApiClientEntity;
import com.sweetk.iitp.api.entity.client.OpenApiClientKeyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {
    
    private final OpenApiClientRepository openApiClientRepository;
    private final OpenApiClientKeyRepository openApiClientKeyRepository;
    
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
    public boolean existsByClientId(String clientId) {
        return openApiClientRepository.existsByClientId(clientId);
    }
    
    @Override
    public boolean existsByClientIdAndIsDeletedFalse(String clientId) {
        return openApiClientRepository.existsByClientIdAndIsDeletedFalse(clientId);
    }
    
    // API Key operations
    @Override
    public Optional<OpenApiClientKeyEntity> findByApiKey(String apiKey) {
        return openApiClientKeyRepository.findByApiKey(apiKey);
    }
    
    @Override
    public Optional<OpenApiClientKeyEntity> findByApiKeyAndIsActiveTrueAndIsDeletedFalse(String apiKey) {
        return openApiClientKeyRepository.findByApiKeyAndIsActiveTrueAndIsDeletedFalse(apiKey);
    }
    
    @Override
    public List<OpenApiClientKeyEntity> findByApiCliIdAndIsActiveTrueAndIsDeletedFalse(Integer apiCliId) {
        return openApiClientKeyRepository.findByApiCliIdAndIsActiveTrueAndIsDeletedFalse(apiCliId);
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
    
    @Override
    public Optional<OpenApiClientEntity> findActiveClientByApiKey(String apiKey) {
        return openApiClientKeyRepository.findByApiKeyAndIsActiveTrueAndIsDeletedFalse(apiKey)
                .map(key -> openApiClientRepository.findById(key.getApiCliId()))
                .orElse(Optional.empty());
    }
    
    // Update operations
    @Override
    @Transactional
    public void updateLatestAccessTime(Integer keyId, OffsetDateTime accessTime) {
        openApiClientKeyRepository.updateLatestAccessTime(keyId, accessTime);
    }
    
    @Override
    @Transactional
    public void updateLatestLoginTime(Integer clientId, OffsetDateTime loginTime) {
        openApiClientRepository.findById(clientId).ifPresent(client -> {
            client.setLatestLoginAt(loginTime);
            openApiClientRepository.save(client);
        });
    }
    
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
} 