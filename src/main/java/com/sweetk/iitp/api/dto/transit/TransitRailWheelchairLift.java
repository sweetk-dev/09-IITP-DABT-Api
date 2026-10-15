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
@Schema(description = "역 휠체어 리프트")
public class TransitRailWheelchairLift {

    @Schema(description = "고유 식별자", example = "1")
    private Integer liftId;

    @Schema(description = "운영 기관", example = "국가철도공단")
    private String operOrg;

    @Schema(description = "노선명", example = "1호선")
    private String lineName;

    @Schema(description = "역명", example = "석수")
    private String stnName;

    @Schema(description = "관리 번호", example = "1")
    private String mngNo;

    @Schema(description = "출입구 번호", example = "1")
    private String exitNo;

    @Schema(description = "상세 위치", example = "1번 출구 계단")
    private String detailLoc;

    @Schema(description = "길이(mm)", example = "1250")
    private Integer lengthMm;

    @Schema(description = "폭(mm)", example = "800")
    private Integer widthMm;

    @Schema(description = "시작 층", example = "B1")
    private String startFloor;

    @Schema(description = "종료 층", example = "1F")
    private String endFloor;

    @Schema(description = "데이터 기준 일자", example = "2026-10-13")
    private LocalDate baseDt;
}
