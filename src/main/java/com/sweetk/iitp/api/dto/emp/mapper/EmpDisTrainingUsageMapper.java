package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisTrainingUsageDto;
import com.sweetk.iitp.api.entity.emp.EmpDisTrainingUsageEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisTrainingUsageMapper {
    public static EmpDisTrainingUsageDto toDto(EmpDisTrainingUsageEntity entity) {
        if (entity == null) return null;
        return EmpDisTrainingUsageDto.builder()
            .orgCategory(entity.getOrgCategory())
            .userCount(entity.getUserCount())
            .employedCount(entity.getEmployedCount())
            .build();
    }
    public static List<EmpDisTrainingUsageDto> toDtoList(List<EmpDisTrainingUsageEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisTrainingUsageMapper::toDto).collect(Collectors.toList());
    }
} 