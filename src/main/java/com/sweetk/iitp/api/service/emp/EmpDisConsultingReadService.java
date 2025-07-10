package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisConsultingDto;
import com.sweetk.iitp.api.entity.emp.EmpDisConsultingEntity;
import com.sweetk.iitp.api.repository.emp.impl.EmpDisConsultingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisConsultingReadService {
    private final EmpDisConsultingRepository repository;

    public List<EmpDisConsultingDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisConsultingDto toDto(EmpDisConsultingEntity e) {
        return EmpDisConsultingDto.builder()
                // 필드 매핑 필요
                .build();
    }
} 