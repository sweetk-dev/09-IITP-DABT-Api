package com.sweetk.iitp.api.dto.emp.converter;

import com.sweetk.iitp.api.dto.emp.EmpDisRegionalStatusDto;
import com.sweetk.iitp.api.entity.emp.EmpDisRegionalStatusEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 지역별 장애인 고용 현황 컨버터
 */
@Component
public class EmpDisRegionalStatusConverter {

    /**
     * Entity를 DTO로 변환
     */
    public EmpDisRegionalStatusDto toDto(EmpDisRegionalStatusEntity entity) {
        if (entity == null) {
            return null;
        }

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

    /**
     * Entity 리스트를 DTO 리스트로 변환
     */
    public List<EmpDisRegionalStatusDto> toDtoList(List<EmpDisRegionalStatusEntity> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * DTO를 Entity로 변환
     */
    public EmpDisRegionalStatusEntity toEntity(EmpDisRegionalStatusDto dto) {
        if (dto == null) {
            return null;
        }

        return EmpDisRegionalStatusEntity.builder()
                .id(dto.getId())
                .region(dto.getRegion())
                .companyCount(dto.getCompanyCount())
                .workerCount(dto.getWorkerCount())
                .obligationCount(dto.getObligationCount())
                .severe2xCount(dto.getSevere2xCount())
                .severe2xRate(dto.getSevere2xRate())
                .build();
    }

    /**
     * DTO 리스트를 Entity 리스트로 변환
     */
    public List<EmpDisRegionalStatusEntity> toEntityList(List<EmpDisRegionalStatusDto> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 