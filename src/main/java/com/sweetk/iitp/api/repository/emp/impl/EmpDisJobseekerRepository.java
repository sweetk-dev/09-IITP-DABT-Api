package com.sweetk.iitp.api.repository.emp.impl;

import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobSeeker;
import com.sweetk.iitp.api.entity.emp.EmpDisJobseekerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

//01. 장애인 구직자 현황


interface EmpDisJobseekerCustomRepository {
    /**
     * 검색 키 + 판(edition) 조건으로 구직자 현황 조회 (최신 등록순, 페이징)
     * @param searchReq 검색 키. null 이면 검색 조건 없이 전체
     * @param baseDt    원천 판 기준일. null 이면 최신 판 (EmpEditionFilter 참고)
     */
    Page<EmpDisJobseekerEntity> findJobSeekersBySearchKey(EmpSearchReqJobSeeker searchReq, LocalDate baseDt, Pageable pageable);
}

@Repository
public interface EmpDisJobseekerRepository extends JpaRepository<EmpDisJobseekerEntity, Integer>, EmpDisJobseekerCustomRepository {


    // 전체 조회(검색 키 없음)도 판(edition) 조건이 필요해 findJobSeekersBySearchKey(null, baseDt, pageable) 로 합쳤다.
    // (기존 findAllOrderByRegDateDesc 와 정렬 기준은 같다: 등록일 DESC, 연번 DESC)


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