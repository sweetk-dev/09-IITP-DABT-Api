package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisStdWorkplaceDto;
import com.sweetk.iitp.api.entity.emp.EmpDisStdWorkplaceEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisStdWorkplaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisStdWorkplaceReadService {
    private final EmpDisStdWorkplaceRepository repository;

    public List<EmpDisStdWorkplaceDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisStdWorkplaceDto toDto(EmpDisStdWorkplaceEntity e) {
        return EmpDisStdWorkplaceDto.builder()
                // 필드 매핑 필요
                .build();
    }
} 