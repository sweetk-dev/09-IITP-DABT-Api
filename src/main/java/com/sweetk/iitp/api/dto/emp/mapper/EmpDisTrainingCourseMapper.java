package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisTrainingCourseDto;
import com.sweetk.iitp.api.entity.emp.EmpDisTrainingCourseEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisTrainingCourseMapper {
    public static EmpDisTrainingCourseDto toDto(EmpDisTrainingCourseEntity entity) {
        if (entity == null) return null;
        return EmpDisTrainingCourseDto.builder()
            .id(entity.getId())
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
    public static List<EmpDisTrainingCourseDto> toDtoList(List<EmpDisTrainingCourseEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisTrainingCourseMapper::toDto).collect(Collectors.toList());
    }
} 