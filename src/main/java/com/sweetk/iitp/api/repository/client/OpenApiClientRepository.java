package com.sweetk.iitp.api.repository.client;

import com.sweetk.iitp.api.constant.DataStatusType;
import com.sweetk.iitp.api.entity.client.OpenApiClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpenApiClientRepository extends JpaRepository<OpenApiClientEntity, Integer> {


    Optional<OpenApiClientEntity> findByApiCliId(Integer apiCliId);

    Optional<OpenApiClientEntity> findByApiCliIdAndStatus(Integer apiCliId, DataStatusType status);

    // Special queries that include deleted items
    @Query("SELECT e FROM OpenApiClientEntity e WHERE e.apiCliId = :apiCliId")
    Optional<OpenApiClientEntity> findByApiCliIdIncludingDeleted(Integer apiCliId);

    @Query("SELECT e FROM OpenApiClientEntity e")
    List<OpenApiClientEntity> findAllIncludingDeleted();



    /* ======================================

    Optional<OpenApiClientEntity> findByApiCliIdAndStatus(Integer apiCliId, DataStatusType status);
    
    Optional<OpenApiClientEntity> findByClientId(String clientId);
    

    boolean existsByClientId(String clientId);
    
    boolean existsByClientIdAndStatus(String clientId, DataStatusType status);
    */

} 