package com.sweetk.iitp.api.repository.emp.impl;

import com.sweetk.iitp.api.entity.emp.EmpDisObligationStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmpDisObligationStatusRepository extends JpaRepository<EmpDisObligationStatusEntity, Integer> {

    /**
     * 기관별 고용의무 현황 조회
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e WHERE e.orgName = :orgName")
    List<EmpDisObligationStatusEntity> findByOrgName(@Param("orgName") String orgName);

    /**
     * 고용률 기준 정렬 조회 (내림차순)
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e ORDER BY e.empRate DESC")
    List<EmpDisObligationStatusEntity> findAllOrderByEmpRateDesc();

    /**
     * 고용률 기준 정렬 조회 (오름차순)
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e ORDER BY e.empRate ASC")
    List<EmpDisObligationStatusEntity> findAllOrderByEmpRateAsc();

    /**
     * 장애인 수 기준 정렬 조회 (내림차순)
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e ORDER BY e.disabledCount DESC")
    List<EmpDisObligationStatusEntity> findAllOrderByDisabledCountDesc();

    /**
     * 상시 근로자 수 기준 정렬 조회 (내림차순)
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e ORDER BY e.workerCount DESC")
    List<EmpDisObligationStatusEntity> findAllOrderByWorkerCountDesc();

    /**
     * 사업체 수 기준 정렬 조회 (내림차순)
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e ORDER BY e.workplaceCount DESC")
    List<EmpDisObligationStatusEntity> findAllOrderByWorkplaceCountDesc();

    /**
     * 고용률 범위별 조회
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e WHERE e.empRate BETWEEN :minRate AND :maxRate ORDER BY e.empRate DESC")
    List<EmpDisObligationStatusEntity> findByEmpRateBetween(@Param("minRate") BigDecimal minRate, @Param("maxRate") BigDecimal maxRate);

    /**
     * 특정 고용률 이상인 기관 조회
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e WHERE e.empRate >= :minRate ORDER BY e.empRate DESC")
    List<EmpDisObligationStatusEntity> findByEmpRateGreaterThanEqual(@Param("minRate") BigDecimal minRate);

    /**
     * 특정 고용률 이하인 기관 조회
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e WHERE e.empRate <= :maxRate ORDER BY e.empRate DESC")
    List<EmpDisObligationStatusEntity> findByEmpRateLessThanEqual(@Param("maxRate") BigDecimal maxRate);

    /**
     * 장애인 수 범위별 조회
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e WHERE e.disabledCount BETWEEN :minCount AND :maxCount ORDER BY e.disabledCount DESC")
    List<EmpDisObligationStatusEntity> findByDisabledCountBetween(@Param("minCount") Integer minCount, @Param("maxCount") Integer maxCount);

    /**
     * 전체 현황 조회 (기관명 순)
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e ORDER BY e.orgName")
    List<EmpDisObligationStatusEntity> findAllOrderByOrgName();

    /**
     * 고용률 상위 N개 기관 조회
     */
    @Query("SELECT e FROM EmpDisObligationStatusEntity e ORDER BY e.empRate DESC")
    List<EmpDisObligationStatusEntity> findTopByEmpRateOrderByEmpRateDesc();
} 