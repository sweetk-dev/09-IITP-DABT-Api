package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationFulfillmentDto;
import com.sweetk.iitp.api.entity.emp.EmpDisObligationFulfillmentEntity;
import com.sweetk.iitp.api.repository.emp.impl.EmpDisObligationFulfillmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisObligationFulfillmentReadService {
    private final EmpDisObligationFulfillmentRepository repository;

    public List<EmpDisObligationFulfillmentDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisObligationFulfillmentDto toDto(EmpDisObligationFulfillmentEntity e) {
        return EmpDisObligationFulfillmentDto.builder()
                .year(e.getYear())
                .companyCount(e.getCompanyCount())
                .fulfilledCount(e.getFulfilledCount())
                .build();
    }
} 