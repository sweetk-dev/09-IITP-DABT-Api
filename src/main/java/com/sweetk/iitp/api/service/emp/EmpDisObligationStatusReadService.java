package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationStatusDto;
import com.sweetk.iitp.api.entity.emp.EmpDisObligationStatusEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisObligationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisObligationStatusReadService {
    private final EmpDisObligationStatusRepository repository;

    public List<EmpDisObligationStatusDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisObligationStatusDto toDto(EmpDisObligationStatusEntity e) {
        return EmpDisObligationStatusDto.builder()
                .orgName(e.getOrgName())
                .workplaceCount(e.getWorkplaceCount())
                .workerCount(e.getWorkerCount())
                .disabledCount(e.getDisabledCount())
                .empRate(e.getEmpRate())
                .build();
    }
} 