package com.sweetk.iitp.api.repository.openapi;

import com.sweetk.iitp.api.constant.SysConstants;
import com.sweetk.iitp.api.entity.openapi.OpenApiAuthKeyEntity;
import com.sweetk.iitp.api.entity.openapi.OpenApiUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class openApiRepositoryImpl implements openApiRepository {
    
    private final OpenApiUserRepository openApiUserRepository;
    private final OpenApiAuthKeyRepository openApiAuthKeyRepository;



    /*****************************
     * API CLIENT AUTH
     *****************************/
    // Combined operations for authentication
    @Override
    public Optional<OpenApiUserEntity> findClientByUserIdIncludingDeleted(Long userId) {
        return openApiUserRepository.findByUserIdIncludingDeleted(userId);
    }


    @Override
    @Transactional
    public void updateLatestLoginTime(OpenApiUserEntity client, OffsetDateTime loginTime) {
        client.setLatestLoginAt(OffsetDateTime.now());
        openApiUserRepository.save(client);
    }


    @Override
    @Transactional
    public void updateLatestAccessTime(OpenApiAuthKeyEntity key, OffsetDateTime accessTime) {
        key.setLatestAccAt(accessTime);
        openApiAuthKeyRepository.save(key);
    }


    /*****************************
     * API CLIENT
     *****************************/

    // find api openapi
    public Optional<OpenApiUserEntity> findClientByUserId(Long userId) {
        return openApiUserRepository.findByUserId(userId);
    }


    // upate api openapi
    public void updateClientDeleteStatus(OpenApiUserEntity client, String deletedBy) {
        client.softDelete(deletedBy);
        openApiUserRepository.save(client);
    }



    /*****************************
     * API CLIENT KEY
     *****************************/

    // find api key
    public Optional<OpenApiAuthKeyEntity> findActiveKeyByApiKey(String apiKey) {
        return openApiAuthKeyRepository.findByAuthKeyAndActiveYn(apiKey, SysConstants.YN_Y);
    }

} 