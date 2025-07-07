package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisIndustryObligationDto;
import com.sweetk.iitp.api.entity.emp.EmpDisIndustryObligationEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisIndustryObligationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisIndustryObligationReadService {
    private final EmpDisIndustryObligationRepository repository;

    public List<EmpDisIndustryObligationDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisIndustryObligationDto toDto(EmpDisIndustryObligationEntity e) {
        return EmpDisIndustryObligationDto.builder()
                .year(e.getYear())
                .industry(e.getIndustry())
                .companyCount(e.getCompanyCount())
                .workerCount(e.getWorkerCount())
                .obligationCount(e.getObligationCount())
                .empRate(e.getEmpRate())
                .build();
    }
} 