package com.sweetk.iitp.api.dto.basic.local;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "경기도 읍면동별·장애유형별·장애정도별 등록장애인 수 (1행 = 시군·구·읍면동·장애유형)")
public class GgDisRegEmd {

    @Schema(description = "원천 기준일 — 시군마다 다르다", example = "2025-08-07")
    private LocalDate baseDt;

    @Schema(description = "시군명", example = "안양시")
    private String sigunName;

    @Schema(description = "일반구명 (구가 없는 시군은 null)", example = "만안구")
    private String guName;

    @Schema(description = "읍면동명 (원천 표기 그대로)", example = "안양1동")
    private String emdName;

    @Schema(description = "장애유형", example = "지체")
    private String disType;

    @Schema(description = "등록장애인 수", example = "250")
    private Integer totCnt;

    @Schema(description = "남", example = "140")
    private Integer maleCnt;

    @Schema(description = "여", example = "110")
    private Integer femaleCnt;

    @Schema(description = "심한 장애", example = "60")
    private Integer sevCnt;

    @Schema(description = "심한 장애 — 남", example = "35")
    private Integer sevMaleCnt;

    @Schema(description = "심한 장애 — 여", example = "25")
    private Integer sevFemaleCnt;

    @Schema(description = "심하지 않은 장애", example = "190")
    private Integer nsevCnt;

    @Schema(description = "심하지 않은 장애 — 남", example = "105")
    private Integer nsevMaleCnt;

    @Schema(description = "심하지 않은 장애 — 여", example = "85")
    private Integer nsevFemaleCnt;

    @Schema(description = "원천 데이터", example = "경기도 내 등록장애인 집계현황(읍면동별·유형별·정도별, 경기데이터드림)")
    private String sourceName;
}
