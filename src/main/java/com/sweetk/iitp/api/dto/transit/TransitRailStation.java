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
@Schema(description = "철도 역사 교통약자 이용 편의 현황")
public class TransitRailStation {

    @Schema(description = "역 고유 식별자", example = "1")
    private Integer stationId;

    @Schema(description = "역 코드 (원천)", example = "3900023")
    private String stnCd;

    @Schema(description = "역명", example = "안양")
    private String stnName;

    @Schema(description = "노선명", example = "")
    private String lineName;

    @Schema(description = "엘리베이터 수", example = "4")
    private Integer elevatorCnt;

    @Schema(description = "에스컬레이터 수", example = "6")
    private Integer escalatorCnt;

    @Schema(description = "휠체어 리프트 수", example = "0")
    private Integer wheelchairLiftCnt;

    @Schema(description = "장애인 경사로 여부 (Y/N)", example = "Y")
    private String disSlopeYn;

    @Schema(description = "장애인 화장실 여부 (Y/N)", example = "Y")
    private String disToiletYn;

    @Schema(description = "일반 화장실 여부 (Y/N)", example = "Y")
    private String genToiletYn;

    @Schema(description = "수유실 여부 (Y/N)", example = "N")
    private String nursingRoomYn;

    @Schema(description = "안내센터 여부 (Y/N)", example = "Y")
    private String infoCenterYn;

    @Schema(description = "위도 (WGS84, 일부 역만 제공)", example = "37.4017")
    private Double latitude;

    @Schema(description = "경도 (WGS84, 일부 역만 제공)", example = "126.9227")
    private Double longitude;

    @Schema(description = "데이터 기준 일자", example = "2026-10-13")
    private LocalDate baseDt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "요청 위치로부터 거리(미터). 위치 기반 검색에서만 제공", example = "420")
    private Integer distance;
}
