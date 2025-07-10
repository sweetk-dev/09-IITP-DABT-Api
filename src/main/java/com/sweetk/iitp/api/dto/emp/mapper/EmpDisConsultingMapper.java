package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisConsultingDto;
import com.sweetk.iitp.api.entity.emp.EmpDisConsultingEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisConsultingMapper {
    public static EmpDisConsultingDto toDto(EmpDisConsultingEntity entity) {
        if (entity == null) return null;
        return EmpDisConsultingDto.builder()
            .id(entity.getId())
            .seqNo(entity.getSeqNo())
            .diagnosisNo(entity.getDiagnosisNo())
            .receiveDate(entity.getReceiveDate())
            .businessNo(entity.getBusinessNo())
            .companyName(entity.getCompanyName())
            .address(entity.getAddress())
            .businessType(entity.getBusinessType())
            .regionalOffice(entity.getRegionalOffice())
            .officeTel(entity.getOfficeTel())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .createdBy(entity.getCreatedBy())
            .updatedBy(entity.getUpdatedBy())
            .build();
    }
    public static List<EmpDisConsultingDto> toDtoList(List<EmpDisConsultingEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisConsultingMapper::toDto).collect(Collectors.toList());
    }
} 