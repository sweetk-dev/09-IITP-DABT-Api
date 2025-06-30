package com.sweetk.iitp.api.repository.client;

import com.sweetk.iitp.api.entity.client.OpenApiClientKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpenApiClientKeyRepository extends JpaRepository<OpenApiClientKeyEntity, Integer> {


    Optional<OpenApiClientKeyEntity> findByApiKeyAndActiveYn(String apiKey, String activeYn);


    // Special queries that include deleted items
    @Query("SELECT e FROM OpenApiClientKeyEntity e WHERE e.apiCliId = :apiCliId")
    List<OpenApiClientKeyEntity> findByApiCliIdIncludingDeleted(Integer apiCliId);

    @Query("SELECT e FROM OpenApiClientKeyEntity e")
    List<OpenApiClientKeyEntity> findAllIncludingDeleted();


    /* ====
    Optional<OpenApiClientKeyEntity> findByApiKey(String apiKey);

    List<OpenApiClientKeyEntity> findByApiCliId(Integer apiCliId);
    
    List<OpenApiClientKeyEntity> findByApiCliIdAndStatus(Integer apiCliId, DataStatusType status);

    boolean existsByApiKey(String apiKey);

     */

} 