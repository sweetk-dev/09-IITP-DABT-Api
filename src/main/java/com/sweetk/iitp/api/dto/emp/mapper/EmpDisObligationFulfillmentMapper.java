package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationFulfillmentDto;
import com.sweetk.iitp.api.entity.emp.EmpDisObligationFulfillmentEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisObligationFulfillmentMapper {
    public static EmpDisObligationFulfillmentDto toDto(EmpDisObligationFulfillmentEntity entity) {
        if (entity == null) return null;
        return EmpDisObligationFulfillmentDto.builder()
            .year(entity.getYear())
            .companyCount(entity.getCompanyCount())
            .fulfilledCount(entity.getFulfilledCount())
            .build();
    }
    public static List<EmpDisObligationFulfillmentDto> toDtoList(List<EmpDisObligationFulfillmentEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisObligationFulfillmentMapper::toDto).collect(Collectors.toList());
    }
} 