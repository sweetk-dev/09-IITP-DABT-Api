package com.sweetk.iitp.api.repository.emp;

import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobPost;
import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobSeeker;
import com.sweetk.iitp.api.entity.emp.*;
import com.sweetk.iitp.api.repository.emp.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmpDisRepositoryImpl implements EmpDisRespository {
    private final EmpDisJobseekerRepository jobseekerRepository;
    private final EmpDisJobPostingRepository jobPostingRepository;

    private final EmpDisBurdenWorkplaceRepository burdenWorkplaceRepository;
    private final EmpDisConsultingRepository consultingRepository;
    private final EmpDisDevSupportOrgRepository devSupportOrgRepository;

    // ... (필요한 나머지 repository도 추가)



    //01. 장애인 구직자 현황 EmpDisJobseekerRepository
    public Page<EmpDisJobseekerEntity> findAllJobSeekers(Pageable pageable) {
        return jobseekerRepository.findAllOrderByRegDateDesc(pageable);
    }

    public Page<EmpDisJobseekerEntity> findJobSeekersBySearchKey(EmpSearchReqJobSeeker searchReq, Pageable pageable) {
        return jobseekerRepository.findJobSeekersBySearchKey(searchReq, pageable);
    }


    //05. 장애인 구인 정보
    public Page<EmpDisJobPostingEntity> findAllJobPosting(Pageable pageable) {
        return jobPostingRepository.findAllOrderBySeqNo(pageable);
    }

    public Page<EmpDisJobPostingEntity> findJobPostBySearchKey(EmpSearchReqJobPost searchReq, Pageable pageable) {
        return jobPostingRepository.findJobPostBySearchKey(searchReq, pageable);
    }




//    // EmpDisBurdenWorkplaceRepository
//    @Override
//    public List<EmpDisBurdenWorkplaceEntity> findAllBurdenWorkplace() {
//        return burdenWorkplaceRepository.findAll();
//    }
//
//    // EmpDisConsultingRepository 주요 메서드 예시
//    @Override
//    public List<EmpDisConsultingEntity> findConsultingByDiagnosisNo(String diagnosisNo) {
//        return consultingRepository.findByDiagnosisNo(diagnosisNo);
//    }
//    @Override
//    public List<EmpDisConsultingEntity> findConsultingByBusinessNo(String businessNo) {
//        return consultingRepository.findByBusinessNo(businessNo);
//    }
//    @Override
//    public List<EmpDisConsultingEntity> findConsultingByCompanyNameContaining(String companyName) {
//        return consultingRepository.findByCompanyNameContaining(companyName);
//    }
//    @Override
//    public List<EmpDisConsultingEntity> findConsultingByBusinessType(String businessType) {
//        return consultingRepository.findByBusinessType(businessType);
//    }
//    @Override
//    public List<EmpDisConsultingEntity> findConsultingByRegionalOffice(String regionalOffice) {
//        return consultingRepository.findByRegionalOffice(regionalOffice);
//    }
//    @Override
//    public List<EmpDisConsultingEntity> findConsultingByReceiveDateBetween(LocalDate startDate, LocalDate endDate) {
//        return consultingRepository.findByReceiveDateBetween(startDate, endDate);
//    }
//    @Override
//    public List<EmpDisConsultingEntity> findConsultingByAddressContaining(String region) {
//        return consultingRepository.findByAddressContaining(region);
//    }
//    @Override
//    public List<EmpDisConsultingEntity> findAllConsultingOrderByReceiveDateDesc() {
//        return consultingRepository.findAllOrderByReceiveDateDesc();
//    }
//    @Override
//    public List<EmpDisConsultingEntity> findConsultingByBusinessTypeAndRegionalOffice(String businessType, String regionalOffice) {
//        return consultingRepository.findByBusinessTypeAndRegionalOffice(businessType, regionalOffice);
//    }
//    @Override
//    public List<EmpDisConsultingEntity> findConsultingByReceiveDateGreaterThanEqual(LocalDate startDate) {
//        return consultingRepository.findByReceiveDateGreaterThanEqual(startDate);
//    }
//    @Override
//    public List<EmpDisConsultingEntity> findAllConsultingOrderBySeqNo() {
//        return consultingRepository.findAllOrderBySeqNo();
//    }
//
//    // EmpDisDevSupportOrgRepository
//    @Override
//    public List<EmpDisDevSupportOrgEntity> findAllDevSupportOrg() {
//        return devSupportOrgRepository.findAll();
//    }
//
//    // EmpDisJobPostingRepository 주요 메서드 예시
//    @Override
//    public List<EmpDisJobPostingEntity> findJobPostingByJobType(String jobType) {
//        return jobPostingRepository.findByJobType(jobType);
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findJobPostingByCompanyType(String companyType) {
//        return jobPostingRepository.findByCompanyType(companyType);
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findJobPostingByOffice(String office) {
//        return jobPostingRepository.findByOffice(office);
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findJobPostingByEmpType(String empType) {
//        return jobPostingRepository.findByEmpType(empType);
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findJobPostingBySalaryType(String salaryType) {
//        return jobPostingRepository.findBySalaryType(salaryType);
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findJobPostingBySalaryAmountBetween(Integer minSalary, Integer maxSalary) {
//        return jobPostingRepository.findBySalaryAmountBetween(minSalary, maxSalary);
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findJobPostingByApplyDateBetween(LocalDate startDate, LocalDate endDate) {
//        return jobPostingRepository.findByApplyDateBetween(startDate, endDate);
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findJobPostingByCompanyNameContaining(String companyName) {
//        return jobPostingRepository.findByCompanyNameContaining(companyName);
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findJobPostingByAddressContaining(String region) {
//        return jobPostingRepository.findByAddressContaining(region);
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findAllJobPostingOrderByApplyDateDesc() {
//        return jobPostingRepository.findAllOrderByApplyDateDesc();
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findAllJobPostingOrderBySalaryAmountDesc() {
//        return jobPostingRepository.findAllOrderBySalaryAmountDesc();
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findJobPostingByJobTypeAndCompanyType(String jobType, String companyType) {
//        return jobPostingRepository.findByJobTypeAndCompanyType(jobType, companyType);
//    }
//    @Override
//    public List<EmpDisJobPostingEntity> findJobPostingBySalaryAmountGreaterThanEqual(Integer minSalary) {
//        return jobPostingRepository.findBySalaryAmountGreaterThanEqual(minSalary);
//    }



} 