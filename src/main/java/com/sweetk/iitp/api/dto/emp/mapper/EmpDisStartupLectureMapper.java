package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisStartupLectureDto;
import com.sweetk.iitp.api.entity.emp.EmpDisStartupLectureEntity;

import java.util.List;
import java.util.stream.Collectors;

public class EmpDisStartupLectureMapper {
    public static EmpDisStartupLectureDto toDto(EmpDisStartupLectureEntity entity) {
        if (entity == null) return null;
        return EmpDisStartupLectureDto.builder()
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
    public static List<EmpDisStartupLectureDto> toDtoList(List<EmpDisStartupLectureEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisStartupLectureMapper::toDto).collect(Collectors.toList());
    }
} 