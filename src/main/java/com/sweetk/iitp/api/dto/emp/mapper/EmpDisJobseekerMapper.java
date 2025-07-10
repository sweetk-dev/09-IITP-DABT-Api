package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisJobseekerDto;
import com.sweetk.iitp.api.entity.emp.EmpDisJobseekerEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisJobseekerMapper {
    public static EmpDisJobseekerDto toDto(EmpDisJobseekerEntity entity) {
        if (entity == null) return null;
        return EmpDisJobseekerDto.builder()
                .seqNo(entity.getSeqNo())
                .regDate(entity.getRegDate())
                .age(entity.getAge())
                .region(entity.getRegion())
                .jobType(entity.getJobType())
                .salaryType(entity.getSalaryType())
                .salaryAmount(entity.getSalaryAmount())
                .disabilityType(entity.getDisabilityType())
                .severity(entity.getSeverity())
                .orgType(entity.getOrgType())
                .build();
    }

    public static List<EmpDisJobseekerDto> toDtoList(List<EmpDisJobseekerEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisJobseekerMapper::toDto).collect(Collectors.toList());
    }
} 