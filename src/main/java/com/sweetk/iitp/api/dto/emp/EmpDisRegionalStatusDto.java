package com.sweetk.iitp.api.dto.emp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 지역별 장애인 고용 현황 DTO
 */
@Schema(description = "지역별 장애인 고용 현황")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDisRegionalStatusDto {
    @Schema(description = "시군구 구분", example = "서울 종로구")
    private String region;
    @Schema(description = "사업체수", example = "411")
    private Integer companyCount;
    @Schema(description = "적용대상 근로자수", example = "274704")
    private Integer workerCount;
    @Schema(description = "의무고용 인원", example = "8561")
    private Integer obligationCount;
    @Schema(description = "중증2배수 적용 장애인 고용인원", example = "7670")
    private Integer severe2xCount;
    @Schema(description = "중증2배수 적용 장애인 고용률", example = "2.79")
    private BigDecimal severe2xRate;
} 