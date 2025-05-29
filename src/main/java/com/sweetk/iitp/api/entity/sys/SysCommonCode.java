package com.sweetk.iitp.api.entity.sys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sys_common_code")
@IdClass(SysCommonCodeId.class)
@Getter
@Setter
@NoArgsConstructor
public class SysCommonCode {

    @Id
    @Column(name = "grp_id", length = 40, nullable = false)
    private String grpId;

    @Id
    @Column(name = "code_id", length = 32, nullable = false)
    private String codeId;

    @Column(name = "grp_nm", length = 80, nullable = false)
    private String grpNm;

    @Column(name = "code_nm", length = 64, nullable = false)
    private String codeNm;

    @Column(name = "parent_grp_id", length = 40)
    private String parentGrpId;

    @Column(name = "parent_code_id", length = 40)
    private String parentCodeId;

    @Column(name = "code_type", length = 1, nullable = false)
    private String codeType;

    @Column(name = "code_lvl")
    private Integer codeLvl;

    @Column(name = "sort_order")
    private Short sortOrder;

    @Column(name = "use_yn", length = 1)
    private String useYn;

    @Column(name = "del_yn", length = 1)
    private String delYn;

    @Column(name = "code_des", length = 255)
    private String codeDes;

    @Column(name = "memo", length = 255)
    private String memo;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by", length = 40)
    private String createdBy;

    @Column(name = "updated_by", length = 40)
    private String updatedBy;

    @Column(name = "deleted_by", length = 40)
    private String deletedBy;
}
