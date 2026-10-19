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
@Schema(description = "경기 버스 정류소")
public class TransitBusStation {

    @Schema(description = "정류소 ID (경기버스정보)", example = "206000001")
    private Long stationId;

    @Schema(description = "정류소 번호(모바일 번호)", example = "25001")
    private String mobileNo;

    @Schema(description = "정류소명", example = "안양역")
    private String stationName;

    @Schema(description = "지역명", example = "안양")
    private String regionName;

    @Schema(description = "관할 관청", example = "안양시")
    private String adminName;

    @Schema(description = "위도 (WGS84)", example = "37.4017")
    private Double latitude;

    @Schema(description = "경도 (WGS84)", example = "126.9227")
    private Double longitude;

    @Schema(description = "중앙차로 정류소 여부 (Y/N)", example = "N")
    private String centerYn;

    @Schema(description = "관할 지역 구분 코드", example = "2")
    private Integer districtCd;

    @Schema(description = "데이터 기준 일자", example = "2026-10-13")
    private LocalDate baseDt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "요청 위치로부터 거리(미터). 위치 기반 검색에서만 제공", example = "420")
    private Integer distance;
}
