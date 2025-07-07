package com.sweetk.iitp.api.dto.emp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisTrainingUsageDto {
    private Integer seqNo;
    private String trainingOrgCategory;
    private Integer trainingUserCount;
    private Integer employmentCount;
} 