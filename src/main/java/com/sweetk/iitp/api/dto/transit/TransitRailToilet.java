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
@Schema(description = "역 화장실 (개소별)")
public class TransitRailToilet {

    @Schema(description = "고유 식별자", example = "1")
    private Integer toiletId;

    @Schema(description = "운영 기관", example = "코레일")
    private String operOrg;

    @Schema(description = "노선명", example = "1호선")
    private String lineName;

    @Schema(description = "역명", example = "안양")
    private String stnName;

    @Schema(description = "장애인 화장실 여부 (Y/N)", example = "Y")
    private String disabledYn;

    @Schema(description = "개소 순번", example = "1")
    private Integer unitSeq;

    @Schema(description = "지상·지하 구분", example = "지상")
    private String groundDv;

    @Schema(description = "층", example = "2")
    private String floorNo;

    @Schema(description = "개찰구 안·밖 구분", example = "외부")
    private String gateInout;

    @Schema(description = "가까운 출입구 번호", example = "1")
    private String exitNo;

    @Schema(description = "상세 위치", example = "대합실")
    private String detailLoc;

    @Schema(description = "화장실 구분", example = "남녀 분리")
    private String toiletKind;

    @Schema(description = "데이터 기준 일자", example = "2026-10-13")
    private LocalDate baseDt;
}
