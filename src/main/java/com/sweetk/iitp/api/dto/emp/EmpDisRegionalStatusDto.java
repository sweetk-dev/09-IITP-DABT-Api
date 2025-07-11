package com.sweetk.iitp.api.dto.emp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 지역별 장애인 고용 현황 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDisRegionalStatusDto {
    private String region;
    private Integer companyCount;
    private Integer workerCount;
    private Integer obligationCount;
    private Integer severe2xCount;
    private BigDecimal severe2xRate;
} 