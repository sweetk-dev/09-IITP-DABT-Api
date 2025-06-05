package com.sweetk.iitp.api.repository;

import com.sweetk.iitp.api.entity.ApiClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiClientRepository extends JpaRepository<ApiClientEntity, Long> {
    Optional<ApiClientEntity> findByApiKey(String apiKey);
    Optional<ApiClientEntity> findByClientId(String clientId);
    boolean existsByApiKey(String apiKey);
    boolean existsByClientId(String clientId);
} 
