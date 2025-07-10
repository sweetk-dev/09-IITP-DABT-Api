package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisEmpIncentiveDto;
import com.sweetk.iitp.api.entity.emp.EmpDisEmpIncentiveEntity;
import com.sweetk.iitp.api.repository.emp.impl.EmpDisEmpIncentiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisEmpIncentiveReadService {
    private final EmpDisEmpIncentiveRepository repository;

    public List<EmpDisEmpIncentiveDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisEmpIncentiveDto toDto(EmpDisEmpIncentiveEntity e) {
        return EmpDisEmpIncentiveDto.builder()
                .region(e.getRegion())
                .industry(e.getIndustry())
                .companyCount(e.getCompanyCount())
                .amount(e.getAmount())
                .paidPerson(e.getPaidPerson())
                .paidYearPerson(e.getPaidYearPerson())
                .build();
    }
} 