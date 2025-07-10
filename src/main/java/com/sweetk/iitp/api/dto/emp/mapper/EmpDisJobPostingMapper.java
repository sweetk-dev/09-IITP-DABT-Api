package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisJobPostingDto;
import com.sweetk.iitp.api.entity.emp.EmpDisJobPostingEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisJobPostingMapper {
    public static EmpDisJobPostingDto toDto(EmpDisJobPostingEntity entity) {
        if (entity == null) return null;
        return EmpDisJobPostingDto.builder()
            .seqNo(entity.getSeqNo())
            .applyDate(entity.getApplyDate())
            .recruitPeriod(entity.getRecruitPeriod())
            .companyName(entity.getCompanyName())
            .jobType(entity.getJobType())
            .empType(entity.getEmpType())
            .salaryType(entity.getSalaryType())
            .salaryAmount(entity.getSalaryAmount())
            .hireType(entity.getHireType())
            .experience(entity.getExperience())
            .education(entity.getEducation())
            .major(entity.getMajor())
            .license(entity.getLicense())
            .address(entity.getAddress())
            .companyType(entity.getCompanyType())
            .office(entity.getOffice())
            .regDate(entity.getRegDate())
            .tel(entity.getTel())
            .build();
    }
    public static List<EmpDisJobPostingDto> toDtoList(List<EmpDisJobPostingEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisJobPostingMapper::toDto).collect(Collectors.toList());
    }
} 