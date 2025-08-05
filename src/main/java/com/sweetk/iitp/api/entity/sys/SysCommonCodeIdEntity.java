package com.sweetk.iitp.api.entity.sys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysCommonCodeIdEntity implements Serializable {

    @Column(name = "grp_id", nullable = false, length = 44)
    private String grpId;

    @Column(name = "code_id", nullable = false, length = 36)
    private String codeId;
}