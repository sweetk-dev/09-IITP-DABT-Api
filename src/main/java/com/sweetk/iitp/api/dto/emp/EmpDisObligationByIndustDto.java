package com.sweetk.iitp.api.dto.emp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisObligationByIndustDto {
    private Integer year;
    private String industry;
    private Integer companyCount;
    private Integer workerCount;
    private Integer obligationCount;
    private Double empRate;
} 