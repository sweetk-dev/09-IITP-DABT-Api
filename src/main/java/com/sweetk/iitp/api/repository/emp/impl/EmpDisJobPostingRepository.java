package com.sweetk.iitp.api.repository.emp.impl;

import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobPost;
import com.sweetk.iitp.api.entity.emp.EmpDisJobPostingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


interface EmpDisJobPostingCustomRepository {
    Page<EmpDisJobPostingEntity> findJobPostBySearchKey(EmpSearchReqJobPost searchReq, Pageable pageable);

}


//05. 장애인 구인 정보
@Repository
public interface EmpDisJobPostingRepository extends JpaRepository<EmpDisJobPostingEntity, Integer>, EmpDisJobPostingCustomRepository {

    /**
     * 전체 조회 (순번순)
     */
    @Query("SELECT e FROM EmpDisJobPostingEntity e ORDER BY e.applyDate DESC, e.seqNo DESC")
    Page<EmpDisJobPostingEntity> findAllOrderBySeqNo(Pageable pageable);






//    /**
//     * 직종별 구인 정보 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e WHERE e.jobType = :jobType ORDER BY e.applyDate DESC")
//    List<EmpDisJobPostingEntity> findByJobType(@Param("jobType") String jobType);
//
//    /**
//     * 기업형태별 구인 정보 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e WHERE e.companyType = :companyType ORDER BY e.applyDate DESC")
//    List<EmpDisJobPostingEntity> findByCompanyType(@Param("companyType") String companyType);
//
//    /**
//     * 담당기관별 구인 정보 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e WHERE e.office = :office ORDER BY e.applyDate DESC")
//    List<EmpDisJobPostingEntity> findByOffice(@Param("office") String office);
//
//    /**
//     * 고용형태별 구인 정보 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e WHERE e.empType = :empType ORDER BY e.applyDate DESC")
//    List<EmpDisJobPostingEntity> findByEmpType(@Param("empType") String empType);
//
//    /**
//     * 임금형태별 구인 정보 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e WHERE e.salaryType = :salaryType ORDER BY e.applyDate DESC")
//    List<EmpDisJobPostingEntity> findBySalaryType(@Param("salaryType") String salaryType);
//
//    /**
//     * 임금 범위별 구인 정보 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e WHERE e.salaryAmount BETWEEN :minSalary AND :maxSalary ORDER BY e.salaryAmount DESC")
//    List<EmpDisJobPostingEntity> findBySalaryAmountBetween(@Param("minSalary") Integer minSalary, @Param("maxSalary") Integer maxSalary);
//
//    /**
//     * 구인신청일자 기간별 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e WHERE e.applyDate BETWEEN :startDate AND :endDate ORDER BY e.applyDate DESC")
//    List<EmpDisJobPostingEntity> findByApplyDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
//
//    /**
//     * 사업장명으로 검색
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e WHERE e.companyName LIKE %:companyName% ORDER BY e.applyDate DESC")
//    List<EmpDisJobPostingEntity> findByCompanyNameContaining(@Param("companyName") String companyName);
//
//    /**
//     * 주소 지역별 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e WHERE e.address LIKE %:region% ORDER BY e.applyDate DESC")
//    List<EmpDisJobPostingEntity> findByAddressContaining(@Param("region") String region);
//
//    /**
//     * 최신 구인신청순 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e ORDER BY e.applyDate DESC, e.id DESC")
//    List<EmpDisJobPostingEntity> findAllOrderByApplyDateDesc();
//
//    /**
//     * 임금 높은순 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e ORDER BY e.salaryAmount DESC")
//    List<EmpDisJobPostingEntity> findAllOrderBySalaryAmountDesc();
//
//    /**
//     * 직종 및 기업형태별 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e WHERE e.jobType = :jobType AND e.companyType = :companyType ORDER BY e.applyDate DESC")
//    List<EmpDisJobPostingEntity> findByJobTypeAndCompanyType(@Param("jobType") String jobType, @Param("companyType") String companyType);
//
//    /**
//     * 특정 임금 이상인 구인 정보 조회
//     */
//    @Query("SELECT e FROM EmpDisJobPostingEntity e WHERE e.salaryAmount >= :minSalary ORDER BY e.salaryAmount DESC")
//    List<EmpDisJobPostingEntity> findBySalaryAmountGreaterThanEqual(@Param("minSalary") Integer minSalary);


} 