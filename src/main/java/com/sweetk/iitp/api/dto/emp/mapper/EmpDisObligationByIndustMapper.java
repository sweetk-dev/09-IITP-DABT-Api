package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationByIndustDto;
import com.sweetk.iitp.api.entity.emp.EmpDisObligationByIndustEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisObligationByIndustMapper {
    public static EmpDisObligationByIndustDto toDto(EmpDisObligationByIndustEntity entity) {
        if (entity == null) return null;
        return EmpDisObligationByIndustDto.builder()
            .year(entity.getYear())
            .industry(entity.getIndustry())
            .companyCount(entity.getCompanyCount())
            .workerCount(entity.getWorkerCount())
            .obligationCount(entity.getObligationCount())
            .empRate(entity.getEmpRate())
            .build();
    }
    public static List<EmpDisObligationByIndustDto> toDtoList(List<EmpDisObligationByIndustEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisObligationByIndustMapper::toDto).collect(Collectors.toList());
    }
} 