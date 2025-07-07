package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisTrainingCourseDto;
import com.sweetk.iitp.api.entity.emp.EmpDisTrainingCourseEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisTrainingCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisTrainingCourseReadService {
    private final EmpDisTrainingCourseRepository repository;

    public List<EmpDisTrainingCourseDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisTrainingCourseDto toDto(EmpDisTrainingCourseEntity e) {
        return EmpDisTrainingCourseDto.builder()
                // 필드 매핑 필요
                .build();
    }
} 