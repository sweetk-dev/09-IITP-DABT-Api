package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "발달장애인훈련센터 이용자현황")
public class EmpDisCenterUsageDto {
    @Schema(description = "훈련기관구분", example = "부산발달장애인훈련센터")
    private String trainOrg;
    @Schema(description = "양성과정 이용자수", example = "80")
    private Integer userCount;
    @Schema(description = "양성과정 취업자수", example = "66")
    private Integer employedCount;
    @Schema(description = "원천 판 기준일 (YYYY-MM-DD). 원천 파일이 기준으로 삼는 날짜이며, 아직 기록되지 않은 데이터는 null", example = "2025-12-31")
    private LocalDate srcBaseDt;
} 