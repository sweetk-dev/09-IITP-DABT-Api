package com.sweetk.iitp.api.dto.emp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisEmpIncentiveDto {
    private String region;
    private String industry;
    private Integer companyCount;
    private Long amount;
    private Integer paidPerson;
    private Integer paidYearPerson;
} 