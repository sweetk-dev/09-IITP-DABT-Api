package com.sweetk.iitp.api.repository.emp;

import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobPost;
import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobSeeker;
import com.sweetk.iitp.api.entity.emp.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface EmpDisRespository {
    //01. 장애인 구직자 현황 EmpDisJobseekerRepository
    Page<EmpDisJobseekerEntity> findAllJobSeekers(Pageable pageable);
    Page<EmpDisJobseekerEntity> findJobSeekersBySearchKey(EmpSearchReqJobSeeker searchReq, Pageable pageable);




    //05. 장애인 구인 정보
    Page<EmpDisJobPostingEntity> findAllJobPosting(Pageable pageable);
    Page<EmpDisJobPostingEntity> findJobPostBySearchKey(EmpSearchReqJobPost searchReq, Pageable pageable);



    // EmpDisBurdenWorkplaceRepository
    List<EmpDisBurdenWorkplaceEntity> findAllBurdenWorkplace();

    // EmpDisConsultingRepository 주요 메서드 예시
    List<EmpDisConsultingEntity> findConsultingByDiagnosisNo(String diagnosisNo);
    List<EmpDisConsultingEntity> findConsultingByBusinessNo(String businessNo);
    List<EmpDisConsultingEntity> findConsultingByCompanyNameContaining(String companyName);
    List<EmpDisConsultingEntity> findConsultingByBusinessType(String businessType);
    List<EmpDisConsultingEntity> findConsultingByRegionalOffice(String regionalOffice);
    List<EmpDisConsultingEntity> findConsultingByReceiveDateBetween(LocalDate startDate, LocalDate endDate);
    List<EmpDisConsultingEntity> findConsultingByAddressContaining(String region);
    List<EmpDisConsultingEntity> findAllConsultingOrderByReceiveDateDesc();
    List<EmpDisConsultingEntity> findConsultingByBusinessTypeAndRegionalOffice(String businessType, String regionalOffice);
    List<EmpDisConsultingEntity> findConsultingByReceiveDateGreaterThanEqual(LocalDate startDate);
    List<EmpDisConsultingEntity> findAllConsultingOrderBySeqNo();

    // EmpDisDevSupportOrgRepository
    List<EmpDisDevSupportOrgEntity> findAllDevSupportOrg();

    // EmpDisJobPostingRepository 주요 메서드 예시
    List<EmpDisJobPostingEntity> findJobPostingByJobType(String jobType);
    List<EmpDisJobPostingEntity> findJobPostingByCompanyType(String companyType);
    List<EmpDisJobPostingEntity> findJobPostingByOffice(String office);
    List<EmpDisJobPostingEntity> findJobPostingByEmpType(String empType);
    List<EmpDisJobPostingEntity> findJobPostingBySalaryType(String salaryType);
    List<EmpDisJobPostingEntity> findJobPostingBySalaryAmountBetween(Integer minSalary, Integer maxSalary);
    List<EmpDisJobPostingEntity> findJobPostingByApplyDateBetween(LocalDate startDate, LocalDate endDate);
    List<EmpDisJobPostingEntity> findJobPostingByCompanyNameContaining(String companyName);
    List<EmpDisJobPostingEntity> findJobPostingByAddressContaining(String region);
    List<EmpDisJobPostingEntity> findAllJobPostingOrderByApplyDateDesc();
    List<EmpDisJobPostingEntity> findAllJobPostingOrderBySalaryAmountDesc();
    List<EmpDisJobPostingEntity> findJobPostingByJobTypeAndCompanyType(String jobType, String companyType);
    List<EmpDisJobPostingEntity> findJobPostingBySalaryAmountGreaterThanEqual(Integer minSalary);

    // EmpDisJobseekerStatusRepository 주요 메서드 예시
    // ... (동일하게 주요 메서드 선언)
    // 나머지 EmpDis*Repository도 주요 메서드 선언
}
