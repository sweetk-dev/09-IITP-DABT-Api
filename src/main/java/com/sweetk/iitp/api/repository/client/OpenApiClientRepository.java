package com.sweetk.iitp.api.repository.client;

import com.sweetk.iitp.api.entity.client.OpenApiClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OpenApiClientRepository extends JpaRepository<OpenApiClientEntity, Integer> {
    
    Optional<OpenApiClientEntity> findByClientId(String clientId);
    
    Optional<OpenApiClientEntity> findByClientIdAndIsDeletedFalse(String clientId);
    
    boolean existsByClientId(String clientId);
    
    boolean existsByClientIdAndIsDeletedFalse(String clientId);
} 