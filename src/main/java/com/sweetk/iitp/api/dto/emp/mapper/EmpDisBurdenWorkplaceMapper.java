package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisBurdenWorkplaceDto;
import com.sweetk.iitp.api.entity.emp.EmpDisBurdenWorkplaceEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisBurdenWorkplaceMapper {
    public static EmpDisBurdenWorkplaceDto toDto(EmpDisBurdenWorkplaceEntity entity) {
        if (entity == null) return null;
        return EmpDisBurdenWorkplaceDto.builder()
            .year(entity.getYear())
            .companyName(entity.getCompanyName())
            .facilityType(entity.getFacilityType())
            .address(entity.getAddress())
            .workItem(entity.getWorkItem())
            .workerCount(entity.getWorkerCount())
            .disabledCount(entity.getDisabledCount())
            .severeCount(entity.getSevereCount())
            .build();
    }
    public static List<EmpDisBurdenWorkplaceDto> toDtoList(List<EmpDisBurdenWorkplaceEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisBurdenWorkplaceMapper::toDto).collect(Collectors.toList());
    }
} 