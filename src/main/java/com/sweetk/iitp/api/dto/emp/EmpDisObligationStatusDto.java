package com.sweetk.iitp.api.dto.emp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @Schema(description = "원천 판 기준일 (YYYY-MM-DD). 원천 파일이 기준으로 삼는 날짜이며, 아직 기록되지 않은 데이터는 null", example = "2025-12-31")
    private LocalDate srcBaseDt;
} 