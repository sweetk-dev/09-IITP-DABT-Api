package com.sweetk.iitp.api.repository.emp;

import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobPost;
import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobSeeker;
import com.sweetk.iitp.api.entity.emp.*;
import com.sweetk.iitp.api.repository.emp.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmpDisRepositoryImpl implements EmpDisRespository {
    private final EmpDisJobseekerRepository jobseekerRepository;
    private final EmpDisCenterUsageRepository centerUsageRepository;
    private final EmpDisObligationStatusRepository obligationStatusRepository;
    private final EmpDisConsultingHisRepository consultingHisRepository;
    private final EmpDisJobPostingRepository jobPostingRepository;
    private final EmpDisBurdenWorkplaceRepository burdenWorkplaceRepository;
    private final EmpDisRegionalStatusRepository regionalStatusRepository;
    private final EmpDisStaffTrainCrsRepository staffTrainCrsRepository;
    private final EmpDisDevSupportOrgRepository devSupportOrgRepository;
    private final EmpDisStartupLectureRepository startupLectureRepository;
    private final EmpDisStdWorkplaceRepository stdWorkplaceRepository;
    private final EmpDisObligationByTypeRepository obligationByTypeRepository;
    private final EmpDisEmpIncentiveRepository empIncentiveRepository;
    private final EmpDisObligationByIndustRepository obligationByIndustRepository;
    private final EmpDisObligationFulfillmentRepository fulfillmentRepository;



    //01. 장애인 구직자 현황 EmpDisJobseekerRepository
    public Page<EmpDisJobseekerEntity> findAllJobSeekers(Pageable pageable) {
        return jobseekerRepository.findAllOrderByRegDateDesc(pageable);
    }

    public Page<EmpDisJobseekerEntity> findJobSeekersBySearchKey(EmpSearchReqJobSeeker searchReq, Pageable pageable) {
        return jobseekerRepository.findJobSeekersBySearchKey(searchReq, pageable);
    }

    //02. 발달장애인훈련센터 이용자현황 EmpDisCenterUsageRepository
    public List<EmpDisCenterUsageEntity> finAllCenterUsages() {
        return centerUsageRepository.findAll();
    }

    //03. 장애인 고용의무 현황 통계 EmpDisObligationStatusRepository
    public List<EmpDisObligationStatusEntity> findAllObligationStatuses() {
        return obligationStatusRepository.findAll();
    }

    //04. 장애인 고용컨설팅 EmpDisConsultingHisRepository
    public List<EmpDisConsultingHisEntity> findAllConsultingHis() {
        return consultingHisRepository.findAll();
    }

    //05. 장애인 구인 정보 EmpDisJobPostingRepository
    public Page<EmpDisJobPostingEntity> findAllJobPosting(Pageable pageable) {
        return jobPostingRepository.findAllOrderBySeqNo(pageable);
    }

    public Page<EmpDisJobPostingEntity> findJobPostBySearchKey(EmpSearchReqJobPost searchReq, Pageable pageable) {
        return jobPostingRepository.findJobPostBySearchKey(searchReq, pageable);
    }

    //06. 장애인고용 부담금감면 연계고용사업장 정보 EmpDisBurdenWorkplaceRepository
    public List<EmpDisBurdenWorkplaceEntity> findAllBurdenWorkplaces() {
        return burdenWorkplaceRepository.findAll();
    }

    //7. 지역별 장애인 고용 현황  EmpDisRegionalStatusRepository
    public List<EmpDisRegionalStatusEntity> findAllRegionalStatuses() {
        return regionalStatusRepository.findAll();
    }

    //08. 고용개발원 교육정보(장애인고용 전문인력 교육과정) EmpDisStaffTrainCrsRepository
    public List<EmpDisStaffTrainCrsEntity> findAllStaffTrainCrs() {
        return staffTrainCrsRepository.findAll();
    }

    //09. 한국장애인개발원 발달장애인 지원 기관 및 제공서비스 EmpDisDevSupportOrgRepository
    public List<EmpDisDevSupportOrgEntity> findAllDevSupportOrg() {
        return devSupportOrgRepository.findAll();
    }

    //10. 장애인기업종합지원센터 창업넷 일반강좌 정보
    public List<EmpDisStartupLectureEntity> findAllStartupLecture() {
        return startupLectureRepository.findAll();
    }


    //11. 장애인 표준사업장 현황 EmpDisStdWorkplaceRepository
    public List<EmpDisStdWorkplaceEntity> findAllStdWorkplace() {
        return stdWorkplaceRepository.findAll();
    }

    //12. 장애인 의무고용 사업체 장애유형별 고용현황 EmpDisObligationByTypeRepository
    public List<EmpDisObligationByTypeEntity> findAllObligationByType() {
        return obligationByTypeRepository.findAll();
    }

    //13. 신규고용장려금 지역별 지급 현황
    public List<EmpDisEmpIncentiveEntity> findAllEmpIncentive() {
        return  empIncentiveRepository.findAll();
    }

    //14. 산업별 의무고용 현황 EmpDisObligationByIndustRepository
    public List<EmpDisObligationByIndustEntity> findAllObligationByIndustry() {
        return obligationByIndustRepository.findAll();
    }

    //15. 장애인 의무고용 현황 EmpDisObligationFulfillmentRepository
    public List<EmpDisObligationFulfillmentEntity> findAllObligationFulfillment() {
        return fulfillmentRepository.findAll();
    }







//    // EmpDisBurdenWorkplaceRepository
//    @Override
//    public List<EmpDisBurdenWorkplaceEntity> findAllBurdenWorkplace() {
//        return burdenWorkplaceRepository.findAll();
//    }
//
//    // EmpDisConsultingHisRepository 주요 메서드 예시
//    @Override
//    public List<EmpDisConsultingHisEntity> findConsultingByDiagnosisNo(String diagnosisNo) {
//        return consultingRepository.findByDiagnosisNo(diagnosisNo);
//    }
//    @Override
//    public List<EmpDisConsultingHisEntity> findConsultingByBusinessNo(String businessNo) {
//        return consultingRepository.findByBusinessNo(businessNo);
//    }
//    @Override
//    public List<EmpDisConsultingHisEntity> findConsultingByCompanyNameContaining(String companyName) {
//        return consultingRepository.findByCompanyNameContaining(companyName);
//    }
//    @Override
//    public List<EmpDisConsultingHisEntity> findConsultingByBusinessType(String businessType) {
//        return consultingRepository.findByBusinessType(businessType);
//    }
//    @Override
//    public List<EmpDisConsultingHisEntity> findConsultingByRegionalOffice(String regionalOffice) {
//        return consultingRepository.findByRegionalOffice(regionalOffice);
//    }
//    @Override
//    public List<EmpDisConsultingHisEntity> findConsultingByReceiveDateBetween(LocalDate startDate, LocalDate endDate) {
//        return consultingRepository.findByReceiveDateBetween(startDate, endDate);
//    }
//    @Override
//    public List<EmpDisConsultingHisEntity> findConsultingByAddressContaining(String region) {
//        return consultingRepository.findByAddressContaining(region);
//    }
//    @Override
//    public List<EmpDisConsultingHisEntity> findAllConsultingOrderByReceiveDateDesc() {
//        return consultingRepository.findAllOrderByReceiveDateDesc();
//    }
//    @Override
//    public List<EmpDisConsultingHisEntity> findConsultingByBusinessTypeAndRegionalOffice(String businessType, String regionalOffice) {
//        return consultingRepository.findByBusinessTypeAndRegionalOffice(businessType, regionalOffice);
//    }
//    @Override
//    public List<EmpDisConsultingHisEntity> findConsultingByReceiveDateGreaterThanEqual(LocalDate startDate) {
//        return consultingRepository.findByReceiveDateGreaterThanEqual(startDate);
//    }
//    @Override
//    public List<EmpDisConsultingHisEntity> findAllConsultingOrderBySeqNo() {
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