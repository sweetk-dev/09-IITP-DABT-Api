package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisStdWorkplaceDto;
import com.sweetk.iitp.api.entity.emp.EmpDisStdWorkplaceEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisStdWorkplaceMapper {
    public static EmpDisStdWorkplaceDto toDto(EmpDisStdWorkplaceEntity entity) {
        if (entity == null) return null;
        return EmpDisStdWorkplaceDto.builder()
            .id(entity.getId())
            .certNo(entity.getCertNo())
            .companyName(entity.getCompanyName())
            .branch(entity.getBranch())
            .ceo(entity.getCeo())
            .businessNo(entity.getBusinessNo())
            .address(entity.getAddress())
            .certDate(entity.getCertDate())
            .tel(entity.getTel())
            .businessItem(entity.getBusinessItem())
            .type(entity.getType())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .createdBy(entity.getCreatedBy())
            .updatedBy(entity.getUpdatedBy())
            .build();
    }
    public static List<EmpDisStdWorkplaceDto> toDtoList(List<EmpDisStdWorkplaceEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisStdWorkplaceMapper::toDto).collect(Collectors.toList());
    }
} 