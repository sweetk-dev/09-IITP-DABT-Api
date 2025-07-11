package com.sweetk.iitp.api.dto.emp.mapper;

import com.sweetk.iitp.api.dto.emp.EmpDisDevSupportOrgDto;
import com.sweetk.iitp.api.entity.emp.EmpDisDevSupportOrgEntity;
import java.util.List;
import java.util.stream.Collectors;

public class EmpDisDevSupportOrgMapper {
    public static EmpDisDevSupportOrgDto toDto(EmpDisDevSupportOrgEntity entity) {
        if (entity == null) return null;
        return EmpDisDevSupportOrgDto.builder()
            .seqNo(entity.getSeqNo())
            .orgName(entity.getOrgName())
            .region(entity.getRegion())
            .dayActivity(entity.getDayActivity())
            .afterschool(entity.getAfterschool())
            .indivPlan(entity.getIndivPlan())
            .parentEdu(entity.getParentEdu())
            .familyRest(entity.getFamilyRest())
            .parentCounsel(entity.getParentCounsel())
            .rightsRelief(entity.getRightsRelief())
            .publicGuardian(entity.getPublicGuardian())
            .childFamilySup(entity.getChildFamilySup())
            .emergencyCare(entity.getEmergencyCare())
            .build();
    }
    public static List<EmpDisDevSupportOrgDto> toDtoList(List<EmpDisDevSupportOrgEntity> entities) {
        return entities == null ? null : entities.stream().map(EmpDisDevSupportOrgMapper::toDto).collect(Collectors.toList());
    }
} 