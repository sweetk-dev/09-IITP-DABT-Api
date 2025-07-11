package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisConsultingHisDto;
import com.sweetk.iitp.api.entity.emp.EmpDisConsultingHisEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisConsultingHisMapper {
    public static EmpDisConsultingHisDto toDto(EmpDisConsultingHisEntity entity) {
        if (entity == null) return null;
        return EmpDisConsultingHisDto.builder()
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
    public static List<EmpDisConsultingHisDto> toDtoList(List<EmpDisConsultingHisEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisConsultingHisMapper::toDto).collect(Collectors.toList());
    }
} 