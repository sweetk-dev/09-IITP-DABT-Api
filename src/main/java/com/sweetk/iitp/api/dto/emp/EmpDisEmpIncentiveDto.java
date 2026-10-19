package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "신규고용장려금 지역별 지급 현황")
public class EmpDisEmpIncentiveDto {
    @Schema(description = "시도구분", example = "강원특별자치도")
    private String region;
    @Schema(description = "업종", example = "A.농업, 임업 및 어업")
    private String industry;
    @Schema(description = "사업체수", example = "1")
    private Integer companyCount;
    @Schema(description = "지급액", example = "3282000")
    private Long amount;
    @Schema(description = "지급순인원", example = "1")
    private Integer paidPerson;
    @Schema(description = "지급연인원", example = "6")
    private Integer paidYearPerson;
    @Schema(description = "원천 판 기준일 (YYYY-MM-DD). 원천 파일이 기준으로 삼는 날짜이며, 아직 기록되지 않은 데이터는 null", example = "2025-12-31")
    private LocalDate srcBaseDt;
} 