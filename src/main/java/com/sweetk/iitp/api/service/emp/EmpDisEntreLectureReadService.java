package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisEntreLectureDto;
import com.sweetk.iitp.api.entity.emp.EmpDisEntreLectureEntity;
import com.sweetk.iitp.api.repository.emp.impl.EmpDisEntreLectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisEntreLectureReadService {
    private final EmpDisEntreLectureRepository repository;

    public List<EmpDisEntreLectureDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisEntreLectureDto toDto(EmpDisEntreLectureEntity e) {
        return EmpDisEntreLectureDto.builder()
                // 필드 매핑 필요
                .build();
    }
} 