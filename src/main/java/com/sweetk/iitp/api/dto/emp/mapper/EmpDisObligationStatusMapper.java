package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationStatusDto;
import com.sweetk.iitp.api.entity.emp.EmpDisObligationStatusEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisObligationStatusMapper {
    public static EmpDisObligationStatusDto toDto(EmpDisObligationStatusEntity entity) {
        if (entity == null) return null;
        return EmpDisObligationStatusDto.builder()
            .orgName(entity.getOrgName())
            .workplaceCount(entity.getWorkplaceCount())
            .workerCount(entity.getWorkerCount())
            .disabledCount(entity.getDisabledCount())
            .empRate(entity.getEmpRate())
            .build();
    }
    public static List<EmpDisObligationStatusDto> toDtoList(List<EmpDisObligationStatusEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisObligationStatusMapper::toDto).collect(Collectors.toList());
    }
} 