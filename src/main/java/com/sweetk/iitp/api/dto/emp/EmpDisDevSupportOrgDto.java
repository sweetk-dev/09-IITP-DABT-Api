package com.sweetk.iitp.api.dto.emp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDisDevSupportOrgDto {
    private Integer id;
    private Integer seqNo;
    private String orgName;
    private String region;
    private Boolean dayActivity;
    private Boolean afterschool;
    private Boolean indivPlan;
    private Boolean parentEdu;
    private Boolean familyRest;
    private Boolean parentCounsel;
    private Boolean rightsRelief;
    private Boolean publicGuardian;
    private Boolean childFamilySup;
    private Boolean emergencyCare;
} 