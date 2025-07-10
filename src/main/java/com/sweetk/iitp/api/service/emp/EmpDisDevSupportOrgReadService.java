package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisDevSupportOrgDto;
import com.sweetk.iitp.api.entity.emp.EmpDisDevSupportOrgEntity;
import com.sweetk.iitp.api.repository.emp.impl.EmpDisDevSupportOrgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisDevSupportOrgReadService {
    private final EmpDisDevSupportOrgRepository repository;

    public List<EmpDisDevSupportOrgDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisDevSupportOrgDto toDto(EmpDisDevSupportOrgEntity e) {
        return EmpDisDevSupportOrgDto.builder()
                // 필드 매핑 필요
                .build();
    }
} 