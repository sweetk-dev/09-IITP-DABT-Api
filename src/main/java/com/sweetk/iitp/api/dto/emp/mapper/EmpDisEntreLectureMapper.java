package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisEntreLectureDto;
import com.sweetk.iitp.api.entity.emp.EmpDisEntreLectureEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisEntreLectureMapper {
    public static EmpDisEntreLectureDto toDto(EmpDisEntreLectureEntity entity) {
        if (entity == null) return null;
        return EmpDisEntreLectureDto.builder()
            .id(entity.getId())
            .year(entity.getYear())
            .onlineType(entity.getOnlineType())
            .category(entity.getCategory())
            .title(entity.getTitle())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .hours(entity.getHours())
            .recruitCount(entity.getRecruitCount())
            .applyStartDate(entity.getApplyStartDate())
            .applyEndDate(entity.getApplyEndDate())
            .orgName(entity.getOrgName())
            .applyCount(entity.getApplyCount())
            .completeCount(entity.getCompleteCount())
            .build();
    }
    public static List<EmpDisEntreLectureDto> toDtoList(List<EmpDisEntreLectureEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisEntreLectureMapper::toDto).collect(Collectors.toList());
    }
} 