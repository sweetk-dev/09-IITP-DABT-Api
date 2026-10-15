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
@Schema(description = "역 엘리베이터 (호기별)")
public class TransitRailElevator {

    @Schema(description = "고유 식별자", example = "1")
    private Integer evId;

    @Schema(description = "운영 기관", example = "코레일")
    private String operOrg;

    @Schema(description = "노선명", example = "1호선")
    private String lineName;

    @Schema(description = "역명", example = "안양")
    private String stnName;

    @Schema(description = "호기 순번", example = "1")
    private Integer unitSeq;

    @Schema(description = "출입구 번호", example = "1")
    private String exitNo;

    @Schema(description = "상세 위치", example = "대합실-1번 출구")
    private String detailLoc;

    @Schema(description = "정원(명)", example = "15")
    private Integer capacityPerson;

    @Schema(description = "적재 하중(kg)", example = "1000")
    private Integer capacityKg;

    @Schema(description = "데이터 기준 일자", example = "2026-10-13")
    private LocalDate baseDt;
}
