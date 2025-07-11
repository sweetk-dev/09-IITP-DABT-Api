package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisCenterUsageDto;
import com.sweetk.iitp.api.entity.emp.EmpDisCenterUsageEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisCenterUsageMapper {
    public static EmpDisCenterUsageDto toDto(EmpDisCenterUsageEntity entity) {
        if (entity == null) return null;
        return EmpDisCenterUsageDto.builder()
            .orgCategory(entity.getOrgCategory())
            .userCount(entity.getUserCount())
            .employedCount(entity.getEmployedCount())
            .build();
    }
    public static List<EmpDisCenterUsageDto> toDtoList(List<EmpDisCenterUsageEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisCenterUsageMapper::toDto).collect(Collectors.toList());
    }
} 