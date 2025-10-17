package com.sweetk.iitp.api.repository.openapi;

import com.sweetk.iitp.api.constant.DataStatusType;
import com.sweetk.iitp.api.entity.openapi.OpenApiUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OpenApiUserRepository extends JpaRepository<OpenApiUserEntity, Long> {


    Optional<OpenApiUserEntity> findByUserId(Long userId);

    Optional<OpenApiUserEntity> findByUserIdAndStatus(Long userId, DataStatusType status);

    // Special queries that include deleted items
    @Query("SELECT e FROM OpenApiUserEntity e WHERE e.userId = :userId")
    Optional<OpenApiUserEntity> findByUserIdIncludingDeleted(Long userId);

    @Query("SELECT e FROM OpenApiUserEntity e")
    List<OpenApiUserEntity> findAllIncludingDeleted();



    /* ======================================

    Optional<OpenApiUserEntity> findByApiCliIdAndStatus(Integer apiCliId, DataStatusType status);
    
    Optional<OpenApiUserEntity> findByClientId(String clientId);
    

    boolean existsByClientId(String clientId);
    
    boolean existsByClientIdAndStatus(String clientId, DataStatusType status);
    */

} 