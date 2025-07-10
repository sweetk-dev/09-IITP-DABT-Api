package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.emp.EmpDisJobPostingDto;
import com.sweetk.iitp.api.dto.emp.EmpDisJobseekerDto;
import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobPost;
import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobSeeker;
import com.sweetk.iitp.api.dto.emp.mapper.EmpDisJobPostingMapper;
import com.sweetk.iitp.api.dto.emp.mapper.EmpDisJobseekerMapper;
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

}
