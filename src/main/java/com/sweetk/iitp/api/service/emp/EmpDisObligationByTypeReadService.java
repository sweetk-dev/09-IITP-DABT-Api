package com.sweetk.iitp.api.service.emp;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationByTypeDto;
import com.sweetk.iitp.api.entity.emp.EmpDisObligationByTypeEntity;
import com.sweetk.iitp.api.repository.emp.EmpDisObligationByTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpDisObligationByTypeReadService {
    private final EmpDisObligationByTypeRepository repository;

    public List<EmpDisObligationByTypeDto> findAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private EmpDisObligationByTypeDto toDto(EmpDisObligationByTypeEntity e) {
        return EmpDisObligationByTypeDto.builder()
                .orgType(e.getOrgType())
                .category(e.getCategory())
                .total(e.getTotal())
                .limbSevere(e.getLimbSevere())
                .limbMild(e.getLimbMild())
                .brainSevere(e.getBrainSevere())
                .brainMild(e.getBrainMild())
                .visionSevere(e.getVisionSevere())
                .visionMild(e.getVisionMild())
                .hearingSevere(e.getHearingSevere())
                .hearingMild(e.getHearingMild())
                .speechSevere(e.getSpeechSevere())
                .speechMild(e.getSpeechMild())
                .intellectual(e.getIntellectual())
                .mentalSevere(e.getMentalSevere())
                .mentalMild(e.getMentalMild())
                .autism(e.getAutism())
                .kidneySevere(e.getKidneySevere())
                .kidneyMild(e.getKidneyMild())
                .heartSevere(e.getHeartSevere())
                .heartMild(e.getHeartMild())
                .lungSevere(e.getLungSevere())
                .lungMild(e.getLungMild())
                .liverSevere(e.getLiverSevere())
                .liverMild(e.getLiverMild())
                .faceSevere(e.getFaceSevere())
                .faceMild(e.getFaceMild())
                .stomaSevere(e.getStomaSevere())
                .stomaMild(e.getStomaMild())
                .epilepsySevere(e.getEpilepsySevere())
                .epilepsyMild(e.getEpilepsyMild())
                .veteran(e.getVeteran())
                .build();
    }
} 