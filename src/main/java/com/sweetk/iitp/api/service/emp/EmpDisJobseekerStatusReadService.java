package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisJobseekerStatusDto;
import com.sweetk.iitp.api.entity.emp.EmpDisJobseekerStatusEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisJobseekerStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisJobseekerStatusReadService {
    private final EmpDisJobseekerStatusRepository repository;

    public List<EmpDisJobseekerStatusDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisJobseekerStatusDto toDto(EmpDisJobseekerStatusEntity e) {
        return EmpDisJobseekerStatusDto.builder()
                // 필드 매핑 필요
                .build();
    }
} 