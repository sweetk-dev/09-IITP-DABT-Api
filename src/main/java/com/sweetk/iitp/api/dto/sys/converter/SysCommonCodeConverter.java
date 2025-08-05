package com.sweetk.iitp.api.dto.sys.converter;

import com.sweetk.iitp.api.dto.sys.SysCommonCodeDto;
import com.sweetk.iitp.api.entity.sys.SysCommonCodeEntity;
import com.sweetk.iitp.api.entity.sys.SysCommonCodeIdEntity;

public class SysCommonCodeConverter {

    public static SysCommonCodeDto toDto(SysCommonCodeEntity entity) {
        if (entity == null) {
            return null;
        }

        return SysCommonCodeDto.builder()
                .grpId(entity.getId().getGrpId())
                .grpNm(entity.getGrpNm())
                .codeId(entity.getId().getCodeId())
                .codeNm(entity.getCodeNm())
                .parentGrpId(entity.getParentGrpId())
                .parentCodeId(entity.getParentCodeId())
                .codeType(entity.getCodeType())
                .codeLvl(entity.getCodeLvl())
                .sortOrder(entity.getSortOrder())
                .useYn(entity.getUseYn())
                .delYn(entity.getDelYn())
                .codeDes(entity.getCodeDes())
                .memo(entity.getMemo())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .deletedBy(entity.getDeletedBy())
                .build();
    }

    public static SysCommonCodeEntity toEntity(SysCommonCodeDto dto) {
        if (dto == null) {
            return null;
        }

        return SysCommonCodeEntity.builder()
                .id(SysCommonCodeIdEntity.builder()
                        .grpId(dto.getGrpId())
                        .codeId(dto.getCodeId())
                        .build())
                .grpNm(dto.getGrpNm())
                .codeNm(dto.getCodeNm())
                .parentGrpId(dto.getParentGrpId())
                .parentCodeId(dto.getParentCodeId())
                .codeType(dto.getCodeType())
                .codeLvl(dto.getCodeLvl())
                .sortOrder(dto.getSortOrder())
                .useYn(dto.getUseYn())
                .delYn(dto.getDelYn())
                .codeDes(dto.getCodeDes())
                .memo(dto.getMemo())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .deletedAt(dto.getDeletedAt())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .deletedBy(dto.getDeletedBy())
                .build();
    }
} 