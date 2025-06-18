package com.sweetk.iitp.api.repository.client;

import com.sweetk.iitp.api.entity.client.OpenApiClientKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OpenApiClientKeyRepository extends JpaRepository<OpenApiClientKeyEntity, Integer> {
    
    Optional<OpenApiClientKeyEntity> findByApiKey(String apiKey);
    
    Optional<OpenApiClientKeyEntity> findByApiKeyAndIsActiveTrueAndIsDeletedFalse(String apiKey);
    
    List<OpenApiClientKeyEntity> findByApiCliIdAndIsDeletedFalse(Integer apiCliId);
    
    List<OpenApiClientKeyEntity> findByApiCliIdAndIsActiveTrueAndIsDeletedFalse(Integer apiCliId);
    
    boolean existsByApiKey(String apiKey);
    
    boolean existsByApiKeyAndIsActiveTrueAndIsDeletedFalse(String apiKey);
    
    @Modifying
    @Query("UPDATE OpenApiClientKeyEntity k SET k.latestAccAt = :accessTime WHERE k.id = :keyId")
    void updateLatestAccessTime(@Param("keyId") Integer keyId, @Param("accessTime") OffsetDateTime accessTime);
    
    @Modifying
    @Query("UPDATE OpenApiClientKeyEntity k SET k.isActive = false WHERE k.apiCliId = :apiCliId")
    void deactivateAllKeysByClientId(@Param("apiCliId") Integer apiCliId);
} 