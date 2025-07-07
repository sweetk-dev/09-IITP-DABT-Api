package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisJobPostingDto;
import com.sweetk.iitp.api.entity.emp.EmpDisJobPostingEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisJobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisJobPostingReadService {
    private final EmpDisJobPostingRepository repository;

    public List<EmpDisJobPostingDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisJobPostingDto toDto(EmpDisJobPostingEntity e) {
        return EmpDisJobPostingDto.builder()
                // 필드 매핑 필요
                .build();
    }
} 