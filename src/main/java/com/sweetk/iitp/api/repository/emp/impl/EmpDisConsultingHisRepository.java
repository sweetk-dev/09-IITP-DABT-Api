package com.sweetk.iitp.api.repository.emp.impl;

import com.sweetk.iitp.api.entity.emp.EmpDisConsultingHisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmpDisConsultingHisRepository extends JpaRepository<EmpDisConsultingHisEntity, Integer> {

    /**
     * 진단번호로 조회
     */
    @Query("SELECT e FROM EmpDisConsultingHisEntity e WHERE e.diagnosisNo = :diagnosisNo")
    List<EmpDisConsultingHisEntity> findByDiagnosisNo(@Param("diagnosisNo") String diagnosisNo);

    /**
     * 사업자등록번호로 조회
     */
    @Query("SELECT e FROM EmpDisConsultingHisEntity e WHERE e.businessNo = :businessNo")
    List<EmpDisConsultingHisEntity> findByBusinessNo(@Param("businessNo") String businessNo);

    /**
     * 사업체명으로 조회
     */
    @Query("SELECT e FROM EmpDisConsultingHisEntity e WHERE e.companyName LIKE %:companyName%")
    List<EmpDisConsultingHisEntity> findByCompanyNameContaining(@Param("companyName") String companyName);

    /**
     * 사업체유형별 조회
     */
    @Query("SELECT e FROM EmpDisConsultingHisEntity e WHERE e.businessType = :businessType ORDER BY e.receiveDate DESC")
    List<EmpDisConsultingHisEntity> findByBusinessType(@Param("businessType") String businessType);

    /**
     * 지역본부별 조회
     */
    @Query("SELECT e FROM EmpDisConsultingHisEntity e WHERE e.regionalOffice = :regionalOffice ORDER BY e.receiveDate DESC")
    List<EmpDisConsultingHisEntity> findByRegionalOffice(@Param("regionalOffice") String regionalOffice);

    /**
     * 접수일자 기간별 조회
     */
    @Query("SELECT e FROM EmpDisConsultingHisEntity e WHERE e.receiveDate BETWEEN :startDate AND :endDate ORDER BY e.receiveDate DESC")
    List<EmpDisConsultingHisEntity> findByReceiveDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 주소 지역별 조회 (강원도, 서울시 등)
     */
    @Query("SELECT e FROM EmpDisConsultingHisEntity e WHERE e.address LIKE %:region% ORDER BY e.receiveDate DESC")
    List<EmpDisConsultingHisEntity> findByAddressContaining(@Param("region") String region);

    /**
     * 최신 접수순 조회
     */
    @Query("SELECT e FROM EmpDisConsultingHisEntity e ORDER BY e.receiveDate DESC, e.id DESC")
    List<EmpDisConsultingHisEntity> findAllOrderByReceiveDateDesc();

    /**
     * 사업체유형 및 지역본부별 조회
     */
    @Query("SELECT e FROM EmpDisConsultingHisEntity e WHERE e.businessType = :businessType AND e.regionalOffice = :regionalOffice ORDER BY e.receiveDate DESC")
    List<EmpDisConsultingHisEntity> findByBusinessTypeAndRegionalOffice(@Param("businessType") String businessType, @Param("regionalOffice") String regionalOffice);

    /**
     * 특정 접수일자 이후 조회
     */
    @Query("SELECT e FROM EmpDisConsultingHisEntity e WHERE e.receiveDate >= :startDate ORDER BY e.receiveDate DESC")
    List<EmpDisConsultingHisEntity> findByReceiveDateGreaterThanEqual(@Param("startDate") LocalDate startDate);

    /**
     * 전체 조회 (순번순)
     */
    @Query("SELECT e FROM EmpDisConsultingHisEntity e ORDER BY e.seqNo")
    List<EmpDisConsultingHisEntity> findAllOrderBySeqNo();
} 