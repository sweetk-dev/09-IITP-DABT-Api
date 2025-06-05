package com.sweetk.iitp.api.entity.sys;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SysCommonCodeIdEntity implements Serializable {
    private String grpId;
    private String codeId;
}