package com.sweetk.iitp.api.dto.transit;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Schema(description = "역 승강장 (승강장별 연단 간격·안전설비)")
public class TransitRailPlatform {

    @Schema(description = "고유 식별자", example = "1")
    private Integer platformId;

    @Schema(description = "운영 기관", example = "코레일")
    private String operOrg;

    @Schema(description = "노선명", example = "1호선")
    private String lineName;

    @Schema(description = "역명", example = "안양")
    private String stnName;

    @Schema(description = "승강장 번호", example = "1")
    private String platformNo;

    @Schema(description = "상하행 구분", example = "상행")
    private String updown;

    @Schema(description = "지상·지하 구분", example = "지상")
    private String groundDv;

    @Schema(description = "층", example = "1")
    private String floorNo;

    @Schema(description = "승강장 간 연결 여부 (Y/N)", example = "Y")
    private String platformConnectYn;

    @Schema(description = "스크린도어 설치 여부 (Y/N)", example = "Y")
    private String screenDoorYn;

    @Schema(description = "안전발판 설치 여부 (Y/N)", example = "N")
    private String safetyPlateYn;

    @Schema(description = "열차-승강장 간격 최소(cm)", example = "5")
    private java.math.BigDecimal gapMinCm;

    @Schema(description = "열차-승강장 간격 최대(cm)", example = "12")
    private java.math.BigDecimal gapMaxCm;

    @Schema(description = "열차-승강장 간격 평균(cm)", example = "8.5")
    private java.math.BigDecimal gapAvgCm;

    @Schema(description = "출입문 수", example = "40")
    private Integer doorCnt;

    @Schema(description = "데이터 기준 일자", example = "2026-10-13")
    private LocalDate baseDt;
}
