package com.sweetk.iitp.api.repository.emp.impl;

import com.sweetk.iitp.api.entity.emp.EmpDisRegionalStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 지역별 장애인 고용 현황 리포지토리
 */
@Repository
public interface EmpDisRegionalStatusRepository extends JpaRepository<EmpDisRegionalStatusEntity, Integer> {

    /**
     * 지역별 조회
     */
    List<EmpDisRegionalStatusEntity> findByRegionContainingOrderBySeqNo(String region);

    /**
     * 고용률 범위별 조회
     */
    List<EmpDisRegionalStatusEntity> findBySevereRateBetweenOrderBySevereRateDesc(BigDecimal minRate, BigDecimal maxRate);

    /**
     * 고용률 기준 상위 조회
     */
    List<EmpDisRegionalStatusEntity> findTop10ByOrderBySevereRateDesc();

    /**
     * 사업체수 기준 상위 조회
     */
    List<EmpDisRegionalStatusEntity> findTop10ByOrderByCompanyCountDesc();

    /**
     * 근로자수 기준 상위 조회
     */
    List<EmpDisRegionalStatusEntity> findTop10ByOrderByWorkerCountDesc();

    /**
     * 의무고용 인원 기준 상위 조회
     */
    List<EmpDisRegionalStatusEntity> findTop10ByOrderByObligationCountDesc();

    /**
     * 중증 장애인 고용인원 기준 상위 조회
     */
    List<EmpDisRegionalStatusEntity> findTop10ByOrderBySevereCountDesc();

    /**
     * 평균 고용률 조회
     */
    @Query("SELECT AVG(e.severe2xRate) FROM EmpDisRegionalStatusEntity e")
    BigDecimal findAverageSevereRate();

    /**
     * 지역별 평균 고용률 조회
     */
    @Query("SELECT e.region, AVG(e.severe2xRate) as avgRate FROM EmpDisRegionalStatusEntity e GROUP BY e.region ORDER BY avgRate DESC")
    List<Object[]> findAverageSevereRateByRegion();

    /**
     * 고용률이 평균 이상인 지역 조회
     */
    @Query("SELECT e FROM EmpDisRegionalStatusEntity e WHERE e.severe2xRate >= (SELECT AVG(e2.severe2xRate) FROM EmpDisRegionalStatusEntity e2) ORDER BY e.severe2xRate DESC")
    List<EmpDisRegionalStatusEntity> findBySevereRateAboveAverage();

    /**
     * 특정 고용률 이상인 지역 조회
     */
    List<EmpDisRegionalStatusEntity> findBySevereRateGreaterThanEqualOrderBySevereRateDesc(BigDecimal rate);

    /**
     * 특정 고용률 이하인 지역 조회
     */
    List<EmpDisRegionalStatusEntity> findBySevereRateLessThanEqualOrderBySevereRateAsc(BigDecimal rate);
} 