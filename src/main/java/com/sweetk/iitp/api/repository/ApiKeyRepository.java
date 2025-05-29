package com.sweetk.iitp.api.repository;

import com.sweetk.iitp.api.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    Optional<ApiKey> findByEncryptedKeyAndActiveTrue(String encryptedKey);
} 