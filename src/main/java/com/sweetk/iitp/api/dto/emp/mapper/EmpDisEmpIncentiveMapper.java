package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisEmpIncentiveDto;
import com.sweetk.iitp.api.entity.emp.EmpDisEmpIncentiveEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisEmpIncentiveMapper {
    public static EmpDisEmpIncentiveDto toDto(EmpDisEmpIncentiveEntity entity) {
        if (entity == null) return null;
        return EmpDisEmpIncentiveDto.builder()
            .region(entity.getRegion())
            .industry(entity.getIndustry())
            .companyCount(entity.getCompanyCount())
            .amount(entity.getAmount())
            .paidPerson(entity.getPaidPerson())
            .paidYearPerson(entity.getPaidYearPerson())
            .build();
    }
    public static List<EmpDisEmpIncentiveDto> toDtoList(List<EmpDisEmpIncentiveEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisEmpIncentiveMapper::toDto).collect(Collectors.toList());
    }
} 