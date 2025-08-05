package com.sweetk.iitp.api.entity.sys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "sys_common_code")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysCommonCodeEntity {

    @EmbeddedId
    private SysCommonCodeIdEntity id;

    @Column(name = "grp_nm", nullable = false, length = 80)
    private String grpNm;

    @Column(name = "code_nm", nullable = false, length = 64)
    private String codeNm;

    @Column(name = "parent_grp_id", length = 40)
    private String parentGrpId;

    @Column(name = "parent_code_id", length = 40)
    private String parentCodeId;

    @Column(name = "code_type", nullable = false, length = 1)
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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by", length = 40)
    private String createdBy;

    @Column(name = "updated_by", length = 40)
    private String updatedBy;

    @Column(name = "deleted_by", length = 40)
    private String deletedBy;
}
