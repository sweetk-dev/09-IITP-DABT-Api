package com.sweetk.iitp.api.repository.openapi;

import com.sweetk.iitp.api.entity.openapi.OpenApiAuthKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpenApiAuthKeyRepository extends JpaRepository<OpenApiAuthKeyEntity, Long> {


    Optional<OpenApiAuthKeyEntity> findByAuthKeyAndActiveYn(String authKey, String activeYn);


    // Special queries that include deleted items
    @Query("SELECT e FROM OpenApiAuthKeyEntity e WHERE e.userId = :userId")
    List<OpenApiAuthKeyEntity> findByUserIdIncludingDeleted(Integer userId);

    @Query("SELECT e FROM OpenApiAuthKeyEntity e")
    List<OpenApiAuthKeyEntity> findAllIncludingDeleted();


    /* ====
    Optional<OpenApiAuthKeyEntity> findByApiKey(String apiKey);

    List<OpenApiAuthKeyEntity> findByApiCliId(Integer apiCliId);
    
    List<OpenApiAuthKeyEntity> findByApiCliIdAndStatus(Integer apiCliId, DataStatusType status);

    boolean existsByApiKey(String apiKey);

     */

} 