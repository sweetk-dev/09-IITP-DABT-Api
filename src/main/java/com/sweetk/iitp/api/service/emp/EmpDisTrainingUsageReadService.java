package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisTrainingUsageDto;
import com.sweetk.iitp.api.entity.emp.EmpDisTrainingUsageEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisTrainingUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisTrainingUsageReadService {
    private final EmpDisTrainingUsageRepository repository;

    public List<EmpDisTrainingUsageDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisTrainingUsageDto toDto(EmpDisTrainingUsageEntity e) {
        return EmpDisTrainingUsageDto.builder()
                .seqNo(e.getSeqNo())
                .trainingOrgCategory(e.getTrainingOrgCategory())
                .trainingUserCount(e.getTrainingUserCount())
                .employmentCount(e.getEmploymentCount())
                .build();
    }
} 