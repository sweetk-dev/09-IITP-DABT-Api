package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisStaffTrainCrsDto;
import com.sweetk.iitp.api.entity.emp.EmpDisStaffTrainCrsEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisStaffTrainCrsMapper {
    public static EmpDisStaffTrainCrsDto toDto(EmpDisStaffTrainCrsEntity entity) {
        if (entity == null) return null;
        return EmpDisStaffTrainCrsDto.builder()
            .courseType(entity.getCourseType())
            .courseName(entity.getCourseName())
            .courseContent(entity.getCourseContent())
            .method(entity.getMethod())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .recruitCount(entity.getRecruitCount())
            .applyStartDate(entity.getApplyStartDate())
            .applyEndDate(entity.getApplyEndDate())
            .applyMethod(entity.getApplyMethod())
            .location(entity.getLocation())
            .target(entity.getTarget())
            .build();
    }
    public static List<EmpDisStaffTrainCrsDto> toDtoList(List<EmpDisStaffTrainCrsEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisStaffTrainCrsMapper::toDto).collect(Collectors.toList());
    }
} 