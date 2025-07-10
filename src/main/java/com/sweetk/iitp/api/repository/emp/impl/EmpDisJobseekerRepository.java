package com.sweetk.iitp.api.repository.emp.impl;

import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobSeeker;
import com.sweetk.iitp.api.entity.emp.EmpDisJobseekerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//01. 장애인 구직자 현황


interface EmpDisJobseekerCustomRepository {
    Page<EmpDisJobseekerEntity> findJobSeekersBySearchKey(EmpSearchReqJobSeeker searchReq, Pageable pageable);
}

@Repository
public interface EmpDisJobseekerRepository extends JpaRepository<EmpDisJobseekerEntity, Integer>, EmpDisJobseekerCustomRepository {


    /**
     * 전체 구직자 현황 조회 (최신순, 페이징)
     */
    @Query("SELECT e FROM EmpDisJobseekerEntity e ORDER BY e.regDate DESC, e.seqNo DESC")
    Page<EmpDisJobseekerEntity> findAllOrderByRegDateDesc(Pageable pageable);


//    /**
//     * 지역별 구직자 현황 조회
//     */
//    @Query("SELECT e FROM EmpDisJobseekerEntity e WHERE e.region = :region ORDER BY e.regDate DESC")
//    List<EmpDisJobseekerEntity> findByRegion(@Param("region") String region);
//
//    /**
//     * 직종별 구직자 현황 조회
//     */
//    @Query("SELECT e FROM EmpDisJobseekerEntity e WHERE e.jobType = :jobType ORDER BY e.regDate DESC")
//    List<EmpDisJobseekerEntity> findByJobType(@Param("jobType") String jobType);
//
//    /**
//     * 장애유형별 구직자 현황 조회
//     */
//    @Query("SELECT e FROM EmpDisJobseekerEntity e WHERE e.disabilityType = :disabilityType ORDER BY e.regDate DESC")
//    List<EmpDisJobseekerEntity> findByDisabilityType(@Param("disabilityType") String disabilityType);
//
//    /**
//     * 중증여부별 구직자 현황 조회
//     */
//    @Query("SELECT e FROM EmpDisJobseekerEntity e WHERE e.severity = :severity ORDER BY e.regDate DESC")
//    List<EmpDisJobseekerEntity> findBySeverity(@Param("severity") String severity);
//
//    /**
//     * 연령대별 구직자 현황 조회
//     */
//    @Query("SELECT e FROM EmpDisJobseekerEntity e WHERE e.age BETWEEN :minAge AND :maxAge ORDER BY e.regDate DESC")
//    List<EmpDisJobseekerEntity> findByAgeBetween(@Param("minAge") Short minAge, @Param("maxAge") Short maxAge);
//
//    /**
//     * 등록일 기간별 구직자 현황 조회
//     */
//    @Query("SELECT e FROM EmpDisJobseekerEntity e WHERE e.regDate BETWEEN :startDate AND :endDate ORDER BY e.regDate DESC")
//    List<EmpDisJobseekerEntity> findByRegDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
//
//    /**
//     * 지역 및 직종별 구직자 현황 조회
//     */
//    @Query("SELECT e FROM EmpDisJobseekerEntity e WHERE e.region = :region AND e.jobType = :jobType ORDER BY e.regDate DESC")
//    List<EmpDisJobseekerEntity> findByRegionAndJobType(@Param("region") String region, @Param("jobType") String jobType);
//
//    /**
//     * 장애유형 및 중증여부별 구직자 현황 조회
//     */
//    @Query("SELECT e FROM EmpDisJobseekerEntity e WHERE e.disabilityType = :disabilityType AND e.severity = :severity ORDER BY e.regDate DESC")
//    List<EmpDisJobseekerEntity> findByDisabilityTypeAndSeverity(@Param("disabilityType") String disabilityType, @Param("severity") String severity);


} 