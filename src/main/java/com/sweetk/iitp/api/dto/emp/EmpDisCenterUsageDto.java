package com.sweetk.iitp.api.dto.emp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisCenterUsageDto {
    private Integer seqNo;
    private String orgCategory;
    private Integer userCount;
    private Integer employedCount;
} 