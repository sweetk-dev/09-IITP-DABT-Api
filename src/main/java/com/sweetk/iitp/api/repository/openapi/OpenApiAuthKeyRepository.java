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

    /**
     * 인증용 키 조회 — 활성(active_yn) 이면서 삭제 표시(del_yn)가 없는 키만 찾는다.
     * 05 Admin 은 키 삭제 시 del_yn='Y' 만 바꾸고 deleted_at 을 비워 둘 수 있어,
     * 엔티티의 @SQLRestriction(deleted_at IS NULL) 만으로는 삭제된 키가 걸러지지 않는다.
     */
    Optional<OpenApiAuthKeyEntity> findByAuthKeyAndActiveYnAndDelYn(String authKey, String activeYn, String delYn);


    // Special queries that include deleted items
    @Query("SELECT e FROM OpenApiAuthKeyEntity e WHERE e.userId = :userId")
    List<OpenApiAuthKeyEntity> findByUserIdIncludingDeleted(Integer userId);

    @Query("SELECT e FROM OpenApiAuthKeyEntity e")
    List<OpenApiAuthKeyEntity> findAllIncludingDeleted();

} 