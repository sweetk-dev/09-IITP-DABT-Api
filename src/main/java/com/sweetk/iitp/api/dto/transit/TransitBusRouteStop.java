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
@Schema(description = "노선이 경유하는 정류소 (경유 순서대로)")
public class TransitBusRouteStop {

    @Schema(description = "경유 순번", example = "1")
    private Integer stationSeq;

    @Schema(description = "회차 지점 여부 (Y/N)", example = "N")
    private String turnYn;

    @Schema(description = "정류소 ID", example = "206000001")
    private Long stationId;

    @Schema(description = "정류소 번호(모바일 번호)", example = "25001")
    private String mobileNo;

    @Schema(description = "정류소명", example = "안양역")
    private String stationName;

    @Schema(description = "위도 (WGS84)", example = "37.4017")
    private Double latitude;

    @Schema(description = "경도 (WGS84)", example = "126.9227")
    private Double longitude;

    @Schema(description = "데이터 기준 일자", example = "2026-10-13")
    private LocalDate baseDt;
}
