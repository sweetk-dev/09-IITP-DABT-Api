package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisObligationByTypeDto;
import com.sweetk.iitp.api.entity.emp.EmpDisObligationByTypeEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisObligationByTypeMapper {
    public static EmpDisObligationByTypeDto toDto(EmpDisObligationByTypeEntity entity) {
        if (entity == null) return null;
        return EmpDisObligationByTypeDto.builder()
            .orgType(entity.getOrgType())
            .category(entity.getCategory())
            .total(entity.getTotal())
            .limbSevere(entity.getLimbSevere())
            .limbMild(entity.getLimbMild())
            .brainSevere(entity.getBrainSevere())
            .brainMild(entity.getBrainMild())
            .visionSevere(entity.getVisionSevere())
            .visionMild(entity.getVisionMild())
            .hearingSevere(entity.getHearingSevere())
            .hearingMild(entity.getHearingMild())
            .speechSevere(entity.getSpeechSevere())
            .speechMild(entity.getSpeechMild())
            .intellectual(entity.getIntellectual())
            .mentalSevere(entity.getMentalSevere())
            .mentalMild(entity.getMentalMild())
            .autism(entity.getAutism())
            .kidneySevere(entity.getKidneySevere())
            .kidneyMild(entity.getKidneyMild())
            .heartSevere(entity.getHeartSevere())
            .heartMild(entity.getHeartMild())
            .lungSevere(entity.getLungSevere())
            .lungMild(entity.getLungMild())
            .liverSevere(entity.getLiverSevere())
            .liverMild(entity.getLiverMild())
            .faceSevere(entity.getFaceSevere())
            .faceMild(entity.getFaceMild())
            .stomaSevere(entity.getStomaSevere())
            .stomaMild(entity.getStomaMild())
            .epilepsySevere(entity.getEpilepsySevere())
            .epilepsyMild(entity.getEpilepsyMild())
            .veteran(entity.getVeteran())
            .build();
    }
    public static List<EmpDisObligationByTypeDto> toDtoList(List<EmpDisObligationByTypeEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisObligationByTypeMapper::toDto).collect(Collectors.toList());
    }
} 