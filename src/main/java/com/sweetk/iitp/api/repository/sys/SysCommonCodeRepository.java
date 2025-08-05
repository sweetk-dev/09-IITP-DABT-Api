package com.sweetk.iitp.api.repository.sys;

import com.sweetk.iitp.api.entity.sys.SysCommonCodeEntity;
import com.sweetk.iitp.api.entity.sys.SysCommonCodeIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysCommonCodeRepository extends JpaRepository<SysCommonCodeEntity, SysCommonCodeIdEntity> {

    /**
     * 그룹 ID로 공통 코드 조회 (사용 중인 것만)
     */
    @Query("SELECT c FROM SysCommonCodeEntity c " +
           "WHERE c.id.grpId = :grpId " +
           "AND c.useYn = 'Y' " +
           "AND c.delYn = 'N' " +
           "ORDER BY c.sortOrder ASC, c.codeLvl ASC")
    List<SysCommonCodeEntity> findByGrpIdAndUseYn(@Param("grpId") String grpId);

    /**
     * 그룹 ID와 코드 타입으로 공통 코드 조회 (사용 중인 것만)
     */
    @Query("SELECT c FROM SysCommonCodeEntity c " +
           "WHERE c.id.grpId = :grpId " +
           "AND c.codeType = :codeType " +
           "AND c.useYn = 'Y' " +
           "AND c.delYn = 'N' " +
           "ORDER BY c.sortOrder ASC, c.codeLvl ASC")
    List<SysCommonCodeEntity> findByGrpIdAndCodeTypeAndUseYn(
            @Param("grpId") String grpId, 
            @Param("codeType") String codeType);

    /**
     * 그룹 ID로 공통 코드 조회 (모든 상태)
     */
    @Query("SELECT c FROM SysCommonCodeEntity c " +
           "WHERE c.id.grpId = :grpId " +
           "ORDER BY c.sortOrder ASC, c.codeLvl ASC")
    List<SysCommonCodeEntity> findByGrpId(@Param("grpId") String grpId);
} 