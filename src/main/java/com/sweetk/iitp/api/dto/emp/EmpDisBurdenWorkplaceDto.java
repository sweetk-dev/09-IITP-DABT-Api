package com.sweetk.iitp.api.dto.emp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisBurdenWorkplaceDto {
    private Integer seqNo;
    private Integer year;
    private String companyName;
    private String facilityType;
    private String address;
    private String workItem;
    private Integer workerCount;
    private Integer disabledCount;
    private Integer severe2xCount;
} 