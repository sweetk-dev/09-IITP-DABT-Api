package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.emp.*;
import com.sweetk.iitp.api.dto.emp.mapper.*;
import com.sweetk.iitp.api.entity.emp.EmpDisJobPostingEntity;
import com.sweetk.iitp.api.entity.emp.EmpDisJobseekerEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmpDisReadService {
    private final EmpDisRespository repository;

    //#####################################
    //## 01. 장애인 구직자 현황
    //#####################################
    public PageRes<EmpDisJobseekerDto> getAllJobSeekers(PageReq page) {
        Page<EmpDisJobseekerEntity> entityPage = repository.findAllJobSeekers(page.toPageable());
        List<EmpDisJobseekerDto> dtoList = EmpDisJobseekerMapper.toDtoList(entityPage.getContent());
        return new PageRes<>(dtoList, page.toPageable(), entityPage.getTotalElements());
    }

    public PageRes<EmpDisJobseekerDto> getJobSeekersBySearchKeys(EmpSearchReqJobSeeker searchReq, PageReq page) {
        Page<EmpDisJobseekerEntity> entityPage = repository.findJobSeekersBySearchKey(searchReq, page.toPageable());
        List<EmpDisJobseekerDto> dtoList = EmpDisJobseekerMapper.toDtoList(entityPage.getContent());
        return new PageRes<>(dtoList, page.toPageable(), entityPage.getTotalElements());
    }


    //#####################################
    //## 02. 발달장애인훈련센터 이용자현황
    //#####################################
    public List<EmpDisCenterUsageDto> getCenterUsage() {
        return EmpDisCenterUsageMapper.toDtoList(repository.finAllCenterUsages());
    }


    //#####################################
    //## 03. 장애인 고용의무 현황 통계
    //#####################################
    public List<EmpDisObligationStatusDto> getObligationStatuses() {
        return EmpDisObligationStatusMapper.toDtoList(repository.findAllObligationStatuses());
    }


    //#####################################
    //## 04. 장애인 고용컨설팅
    //#####################################
    public List<EmpDisConsultingHisDto> getConsultingHis() {
        return EmpDisConsultingHisMapper.toDtoList(repository.findAllConsultingHis());
    }


    //#####################################
    //## 05. 장애인 구인 정보
    //#####################################
    public PageRes<EmpDisJobPostingDto> getAllJobPosting(PageReq page) {
        Page<EmpDisJobPostingEntity> entityPage = repository.findAllJobPosting(page.toPageable());
        List<EmpDisJobPostingDto> dtoList = EmpDisJobPostingMapper.toDtoList(entityPage.getContent());
        return new PageRes<>(dtoList, page.toPageable(), entityPage.getTotalElements());
    }

    public PageRes<EmpDisJobPostingDto> getJobPostBySearchKeys(EmpSearchReqJobPost searchReq, PageReq page) {
        Page<EmpDisJobPostingEntity> entityPage = repository.findJobPostBySearchKey(searchReq, page.toPageable());
        List<EmpDisJobPostingDto> dtoList = EmpDisJobPostingMapper.toDtoList(entityPage.getContent());
        return new PageRes<>(dtoList, page.toPageable(), entityPage.getTotalElements());
    }


    //#####################################
    //## 06. 장애인고용 부담금감면 연계고용사업장 정보
    //#####################################
    public List<EmpDisBurdenWorkplaceDto> getBurdenRedctWorkplace() {
        return EmpDisBurdenWorkplaceMapper.toDtoList(repository.findAllBurdenWorkplaces());
    }


    //#####################################
    //## 7. 지역별 장애인 고용 현황
    //#####################################
    public List<EmpDisRegionalStatusDto> getEmpStatusByRegion() {
        return EmpDisRegionalStatusMapper.toDtoList(repository.findAllRegionalStatuses());
    }


    //#####################################
    //## 08. 고용개발원 교육정보(장애인고용 전문인력 교육과정)
    //#####################################
    public List<EmpDisStaffTrainCrsDto> getStaffTrainCrs() {
        return EmpDisStaffTrainCrsMapper.toDtoList(repository.findAllStaffTrainCrs());
    }


    //#####################################
    //## 09. 한국장애인개발원 발달장애인 지원 기관 및 제공서비스
    //#####################################
    public List<EmpDisDevSupportOrgDto> getDevDisSupportOrg() {
        return EmpDisDevSupportOrgMapper.toDtoList(repository.findAllDevSupportOrg());
    }


    //#####################################
    //## 10. 장애인기업종합지원센터 창업넷 일반강좌 정보
    //#####################################
    public List<EmpDisStartupLectureDto> getStartupLecture() {
        return EmpDisStartupLectureMapper.toDtoList(repository.findAllStartupLecture());
    }


    //#####################################
    //## 11. 장애인 표준사업장 현황
    //#####################################
    public List<EmpDisStdWorkplaceDto> getStandardWorkplace() {
        return EmpDisStdWorkplaceMapper.toDtoList(repository.findAllStdWorkplace());
    }


    //#####################################
    //##12. 장애인 의무고용 사업체 장애유형별 고용현황
    //#####################################
    public List<EmpDisObligationByTypeDto> getObligationByType() {
        return EmpDisObligationByTypeMapper.toDtoList(repository.findAllObligationByType());
    }


    //#####################################
    //## 13. 신규고용장려금 지역별 지급 현황
    //#####################################
    public List<EmpDisEmpIncentiveDto> getEmpIncentive() {
        return EmpDisEmpIncentiveMapper.toDtoList(repository.findAllEmpIncentive());
    }


    //#####################################
    //## 14. 산업별 의무고용 현황
    //#####################################
    public List<EmpDisObligationByIndustDto> getObligationByIndustry() {
        return EmpDisObligationByIndustMapper.toDtoList(repository.findAllObligationByIndustry());
    }


    //#####################################
    //## 15. 장애인 의무고용 현황
    //#####################################
    public List<EmpDisObligationFulfillmentDto> getObligationFulfillment() {
        return EmpDisObligationFulfillmentMapper.toDtoList(repository.findAllObligationFulfillment());
    }






}
