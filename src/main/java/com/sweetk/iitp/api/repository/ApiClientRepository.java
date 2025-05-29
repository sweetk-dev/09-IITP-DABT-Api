package com.sweetk.iitp.api.repository;

import com.sweetk.iitp.api.entity.ApiClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiClientRepository extends JpaRepository<ApiClient, Long> {
    Optional<ApiClient> findByApiKey(String apiKey);
    Optional<ApiClient> findByClientId(String clientId);
    boolean existsByApiKey(String apiKey);
    boolean existsByClientId(String clientId);
} 