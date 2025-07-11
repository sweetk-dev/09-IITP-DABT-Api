package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "발달장애인훈련센터 이용자현황")
public class EmpDisCenterUsageDto {
    @Schema(description = "순번", example = "1")
    private Integer seqNo;
    @Schema(description = "훈련기관구분", example = "부산발달장애인훈련센터")
    private String trainOrg;
    @Schema(description = "양성과정 이용자수", example = "80")
    private Integer userCount;
    @Schema(description = "양성과정 취업자수", example = "66")
    private Integer employedCount;
} 