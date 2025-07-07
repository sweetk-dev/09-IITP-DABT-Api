package com.sweetk.iitp.api.repository.emp;

import com.sweetk.iitp.api.entity.emp.EmpDisJobseekerStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmpDisJobseekerStatusRepository extends JpaRepository<EmpDisJobseekerStatusEntity, Integer> {

    /**
     * 지역별 구직자 현황 조회
     */
    @Query("SELECT e FROM EmpDisJobseekerStatusEntity e WHERE e.region = :region ORDER BY e.regDate DESC")
    List<EmpDisJobseekerStatusEntity> findByRegion(@Param("region") String region);

    /**
     * 직종별 구직자 현황 조회
     */
    @Query("SELECT e FROM EmpDisJobseekerStatusEntity e WHERE e.jobType = :jobType ORDER BY e.regDate DESC")
    List<EmpDisJobseekerStatusEntity> findByJobType(@Param("jobType") String jobType);

    /**
     * 장애유형별 구직자 현황 조회
     */
    @Query("SELECT e FROM EmpDisJobseekerStatusEntity e WHERE e.disabilityType = :disabilityType ORDER BY e.regDate DESC")
    List<EmpDisJobseekerStatusEntity> findByDisabilityType(@Param("disabilityType") String disabilityType);

    /**
     * 중증여부별 구직자 현황 조회
     */
    @Query("SELECT e FROM EmpDisJobseekerStatusEntity e WHERE e.severity = :severity ORDER BY e.regDate DESC")
    List<EmpDisJobseekerStatusEntity> findBySeverity(@Param("severity") String severity);

    /**
     * 연령대별 구직자 현황 조회
     */
    @Query("SELECT e FROM EmpDisJobseekerStatusEntity e WHERE e.age BETWEEN :minAge AND :maxAge ORDER BY e.regDate DESC")
    List<EmpDisJobseekerStatusEntity> findByAgeBetween(@Param("minAge") Short minAge, @Param("maxAge") Short maxAge);

    /**
     * 등록일 기간별 구직자 현황 조회
     */
    @Query("SELECT e FROM EmpDisJobseekerStatusEntity e WHERE e.regDate BETWEEN :startDate AND :endDate ORDER BY e.regDate DESC")
    List<EmpDisJobseekerStatusEntity> findByRegDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 지역 및 직종별 구직자 현황 조회
     */
    @Query("SELECT e FROM EmpDisJobseekerStatusEntity e WHERE e.region = :region AND e.jobType = :jobType ORDER BY e.regDate DESC")
    List<EmpDisJobseekerStatusEntity> findByRegionAndJobType(@Param("region") String region, @Param("jobType") String jobType);

    /**
     * 장애유형 및 중증여부별 구직자 현황 조회
     */
    @Query("SELECT e FROM EmpDisJobseekerStatusEntity e WHERE e.disabilityType = :disabilityType AND e.severity = :severity ORDER BY e.regDate DESC")
    List<EmpDisJobseekerStatusEntity> findByDisabilityTypeAndSeverity(@Param("disabilityType") String disabilityType, @Param("severity") String severity);

    /**
     * 전체 구직자 현황 조회 (최신순)
     */
    @Query("SELECT e FROM EmpDisJobseekerStatusEntity e ORDER BY e.regDate DESC, e.id DESC")
    List<EmpDisJobseekerStatusEntity> findAllOrderByRegDateDesc();
} 