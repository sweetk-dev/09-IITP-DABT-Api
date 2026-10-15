package com.sweetk.iitp.api.dto.basic.local;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "읍면동 등록장애인 시군별 판(원천 기준일)")
public class GgDisRegEdition {

    @Schema(description = "시군명", example = "안양시")
    private String sigunName;

    @Schema(description = "원천 기준일", example = "2025-08-07")
    private LocalDate baseDt;

    @Schema(description = "그 판의 행 수", example = "459")
    private Integer rowCount;

    @Schema(description = "등록장애인 수 합계", example = "21744")
    private Long totalCnt;

    @Schema(description = "그 시군의 최신 판 여부", example = "true")
    private Boolean latest;
}
