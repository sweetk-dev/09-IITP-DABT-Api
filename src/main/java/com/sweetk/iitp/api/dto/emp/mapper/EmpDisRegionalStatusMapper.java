package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisRegionalStatusDto;
import com.sweetk.iitp.api.entity.emp.EmpDisRegionalStatusEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisRegionalStatusMapper {
    public static EmpDisRegionalStatusDto toDto(EmpDisRegionalStatusEntity entity) {
        if (entity == null) return null;
        return EmpDisRegionalStatusDto.builder()
            .id(entity.getId())
            .region(entity.getRegion())
            .companyCount(entity.getCompanyCount())
            .workerCount(entity.getWorkerCount())
            .obligationCount(entity.getObligationCount())
            .severe2xCount(entity.getSevere2xCount())
            .severe2xRate(entity.getSevere2xRate())
            .build();
    }
    public static List<EmpDisRegionalStatusDto> toDtoList(List<EmpDisRegionalStatusEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisRegionalStatusMapper::toDto).collect(Collectors.toList());
    }
} 