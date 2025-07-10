package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationByIndustDto;
import com.sweetk.iitp.api.entity.emp.EmpDisIndustryObligationEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisObligationByIndustRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisObligationByIndustReadService {
    private final EmpDisObligationByIndustRepository repository;

    public List<EmpDisObligationByIndustDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisObligationByIndustDto toDto(EmpDisIndustryObligationEntity e) {
        return EmpDisObligationByIndustDto.builder()
                .year(e.getYear())
                .industry(e.getIndustry())
                .companyCount(e.getCompanyCount())
                .workerCount(e.getWorkerCount())
                .obligationCount(e.getObligationCount())
                .empRate(e.getEmpRate())
                .build();
    }
} 