package com.sweetk.iitp.api.repository.emp;

import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobPost;
import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobSeeker;
import com.sweetk.iitp.api.entity.emp.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * 장애인 고용(emp_dis_*) 데이터 조회 창구.
 * <p>baseDt(원천 판 기준일) 인자는 연도 컬럼이 없어 여러 판이 함께 쌓이는 9개 표에만 있다.
 * null 이면 최신 판, 값이 있으면 그 판만 조회한다 (support/EmpEditionFilter 참고).
 * 연도 컬럼이 있는 5개 표는 연도마다 한 판이라 판 조건 없이 최신 연도 먼저 돌려준다.</p>
 */
public interface EmpDisRespository {
    //01. 장애인 구직자 현황 EmpDisJobseekerRepository
    Page<EmpDisJobseekerEntity> findAllJobSeekers(LocalDate baseDt, Pageable pageable);
    Page<EmpDisJobseekerEntity> findJobSeekersBySearchKey(EmpSearchReqJobSeeker searchReq, LocalDate baseDt, Pageable pageable);

    //02. 발달장애인훈련센터 이용자현황 EmpDisCenterUsageRepository
    List<EmpDisCenterUsageEntity> finAllCenterUsages(LocalDate baseDt);

    //03. 장애인 고용의무 현황 통계 EmpDisObligationStatusRepository
    List<EmpDisObligationStatusEntity> findAllObligationStatuses(LocalDate baseDt);

    //04. 장애인 고용컨설팅 EmpDisConsultingHisRepository
    List<EmpDisConsultingHisEntity> findAllConsultingHis(LocalDate baseDt);

    //05. 장애인 구인 정보 EmpDisJobPostingRepository
    Page<EmpDisJobPostingEntity> findAllJobPosting(LocalDate baseDt, Pageable pageable);
    Page<EmpDisJobPostingEntity> findJobPostBySearchKey(EmpSearchReqJobPost searchReq, LocalDate baseDt, Pageable pageable);

    //06. 장애인고용 부담금감면 연계고용사업장 정보 EmpDisBurdenWorkplaceRepository
    List<EmpDisBurdenWorkplaceEntity> findAllBurdenWorkplaces();

    //7. 지역별 장애인 고용 현황 EmpDisRegionalStatusRepository
    List<EmpDisRegionalStatusEntity> findAllRegionalStatuses();

    //08. 고용개발원 교육정보(장애인고용 전문인력 교육과정) EmpDisStaffTrainCrsRepository
    List<EmpDisStaffTrainCrsEntity> findAllStaffTrainCrs(LocalDate baseDt);

    //09. 한국장애인개발원 발달장애인 지원 기관 및 제공서비스
    List<EmpDisDevSupportOrgEntity> findAllDevSupportOrg(LocalDate baseDt);

    //10. 장애인기업종합지원센터 창업넷 일반강좌 정보 EmpDisStartupLectureRepository
    List<EmpDisStartupLectureEntity> findAllStartupLecture();

    //11. 장애인 표준사업장 현황
    List<EmpDisStdWorkplaceEntity> findAllStdWorkplace(LocalDate baseDt);

    //12. 장애인 의무고용 사업체 장애유형별 고용현황 EmpDisObligationByTypeRepository
    List<EmpDisObligationByTypeEntity> findAllObligationByType();

    //13. 신규고용장려금 지역별 지급 현황 EmpDisEmpIncentiveRepository
    List<EmpDisEmpIncentiveEntity> findAllEmpIncentive(LocalDate baseDt);

    //14. 산업별 의무고용 현황 EmpDisObligationByIndustRepository
    List<EmpDisObligationByIndustEntity> findAllObligationByIndustry();

    //15. 장애인 의무고용 현황 EmpDisObligationFulfillmentRepository
    List<EmpDisObligationFulfillmentEntity> findAllObligationFulfillment();







//    // EmpDisBurdenWorkplaceRepository
//    List<EmpDisBurdenWorkplaceEntity> findAllBurdenWorkplace();
//
//    // EmpDisConsultingHisRepository 주요 메서드 예시
//    List<EmpDisConsultingHisEntity> findConsultingByDiagnosisNo(String diagnosisNo);
//    List<EmpDisConsultingHisEntity> findConsultingByBusinessNo(String businessNo);
//    List<EmpDisConsultingHisEntity> findConsultingByCompanyNameContaining(String companyName);
//    List<EmpDisConsultingHisEntity> findConsultingByBusinessType(String businessType);
//    List<EmpDisConsultingHisEntity> findConsultingByRegionalOffice(String regionalOffice);
//    List<EmpDisConsultingHisEntity> findConsultingByReceiveDateBetween(LocalDate startDate, LocalDate endDate);
//    List<EmpDisConsultingHisEntity> findConsultingByAddressContaining(String region);
//    List<EmpDisConsultingHisEntity> findAllConsultingOrderByReceiveDateDesc();
//    List<EmpDisConsultingHisEntity> findConsultingByBusinessTypeAndRegionalOffice(String businessType, String regionalOffice);
//    List<EmpDisConsultingHisEntity> findConsultingByReceiveDateGreaterThanEqual(LocalDate startDate);
//    List<EmpDisConsultingHisEntity> findAllConsultingOrderBySeqNo();
//
//    // EmpDisDevSupportOrgRepository
//    List<EmpDisDevSupportOrgEntity> findAllDevSupportOrg();

    // EmpDisJobPostingRepository 주요 메서드 예시
//    List<EmpDisJobPostingEntity> findJobPostingByJobType(String jobType);
//    List<EmpDisJobPostingEntity> findJobPostingByCompanyType(String companyType);
//    List<EmpDisJobPostingEntity> findJobPostingByOffice(String office);
//    List<EmpDisJobPostingEntity> findJobPostingByEmpType(String empType);
//    List<EmpDisJobPostingEntity> findJobPostingBySalaryType(String salaryType);
//    List<EmpDisJobPostingEntity> findJobPostingBySalaryAmountBetween(Integer minSalary, Integer maxSalary);
//    List<EmpDisJobPostingEntity> findJobPostingByApplyDateBetween(LocalDate startDate, LocalDate endDate);
//    List<EmpDisJobPostingEntity> findJobPostingByCompanyNameContaining(String companyName);
//    List<EmpDisJobPostingEntity> findJobPostingByAddressContaining(String region);
//    List<EmpDisJobPostingEntity> findAllJobPostingOrderByApplyDateDesc();
//    List<EmpDisJobPostingEntity> findAllJobPostingOrderBySalaryAmountDesc();
//    List<EmpDisJobPostingEntity> findJobPostingByJobTypeAndCompanyType(String jobType, String companyType);
//    List<EmpDisJobPostingEntity> findJobPostingBySalaryAmountGreaterThanEqual(Integer minSalary);

    // EmpDisJobseekerStatusRepository 주요 메서드 예시
    // ... (동일하게 주요 메서드 선언)
    // 나머지 EmpDis*Repository도 주요 메서드 선언
}
