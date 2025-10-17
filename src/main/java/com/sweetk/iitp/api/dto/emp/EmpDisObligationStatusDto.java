package com.sweetk.iitp.api.dto.emp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "장애인 고용의무 현황 통계")
public class EmpDisObligationStatusDto {
    @Schema(description = "기관", example = "중앙행정기관")
    private String orgName;
    @Schema(description = "대상 사업체(개소)", example = "54")
    private Integer workplaceCount;
    @Schema(description = "상시 근로자(명)", example = "207070")
    private Integer workerCount;
    @Schema(description = "장애인(명)", example = "7338")
    private Integer disabledCount;
    @Schema(description = "고용률", example = "3.54")
    private BigDecimal empRate;
} 