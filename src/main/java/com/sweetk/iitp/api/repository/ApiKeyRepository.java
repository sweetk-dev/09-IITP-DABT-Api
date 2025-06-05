package com.sweetk.iitp.api.repository;

import com.sweetk.iitp.api.entity.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity, Long> {
    Optional<ApiKeyEntity> findByEncryptedKeyAndActiveTrue(String encryptedKey);
} 
