package com.sweetk.iitp.api.dto.sys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysCommonCodeDto {

    private String grpId;
    private String grpNm;
    private String codeId;
    private String codeNm;
    private String parentGrpId;
    private String parentCodeId;
    private String codeType;
    private Integer codeLvl;
    private Short sortOrder;
    private String useYn;
    private String delYn;
    private String codeDes;
    private String memo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String createdBy;
    private String updatedBy;
    private String deletedBy;
} 