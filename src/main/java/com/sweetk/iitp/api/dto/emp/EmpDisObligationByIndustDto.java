package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "산업별 의무고용 현황")
public class EmpDisObligationByIndustDto {
    @Schema(description = "연도", example = "2023")
    private Integer year;
    @Schema(description = "산업구분", example = "A.농업, 임업 및 어업")
    private String industry;
    @Schema(description = "사업체수", example = "111")
    private Integer companyCount;
    @Schema(description = "적용대상근로자수", example = "19183")
    private Integer workerCount;
    @Schema(description = "의무고용인원", example = "535")
    private Integer obligationCount;
    @Schema(description = "장애인 고용률", example = "2.86")
    private Double empRate;
} 