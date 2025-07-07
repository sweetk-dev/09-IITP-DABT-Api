package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisBurdenWorkplaceDto;
import com.sweetk.iitp.api.entity.emp.EmpDisBurdenWorkplaceEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisBurdenWorkplaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisBurdenWorkplaceReadService {
    private final EmpDisBurdenWorkplaceRepository repository;

    public List<EmpDisBurdenWorkplaceDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisBurdenWorkplaceDto toDto(EmpDisBurdenWorkplaceEntity e) {
        return EmpDisBurdenWorkplaceDto.builder()
                .seqNo(e.getSeqNo())
                .year(e.getYear())
                .companyName(e.getCompanyName())
                .facilityType(e.getFacilityType())
                .address(e.getAddress())
                .workItem(e.getWorkItem())
                .workerCount(e.getWorkerCount())
                .disabledCount(e.getDisabledCount())
                .severeCount(e.getSevereCount())
                .build();
    }
} 