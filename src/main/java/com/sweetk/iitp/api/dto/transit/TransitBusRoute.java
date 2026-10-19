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
@Schema(description = "경기 버스 노선")
public class TransitBusRoute {

    @Schema(description = "노선 ID (경기버스정보)", example = "200000085")
    private Long routeId;

    @Schema(description = "노선 번호", example = "5-1")
    private String routeName;

    @Schema(description = "노선 유형 코드", example = "13")
    private Integer routeTypeCd;

    @Schema(description = "노선 유형명", example = "일반형시내버스")
    private String routeTypeName;

    @Schema(description = "운행 지역", example = "안양,군포")
    private String regionName;

    @Schema(description = "관할 관청", example = "안양시")
    private String adminName;

    @Schema(description = "기점 정류소 ID", example = "206000001")
    private Long startStationId;

    @Schema(description = "기점 정류소명", example = "범계역")
    private String startStationName;

    @Schema(description = "종점 정류소 ID", example = "206000002")
    private Long endStationId;

    @Schema(description = "종점 정류소명", example = "안양역")
    private String endStationName;

    @Schema(description = "운수사명", example = "삼영운수")
    private String companyName;

    @Schema(description = "운수사 전화번호", example = "031-000-0000")
    private String companyTel;

    @Schema(description = "평일 출퇴근 배차 간격(분)", example = "10")
    private Integer peekAlloc;

    @Schema(description = "평일 평시 배차 간격(분)", example = "15")
    private Integer npeekAlloc;

    @Schema(description = "토요일 출퇴근 배차 간격(분)", example = "")
    private Integer satPeekAlloc;

    @Schema(description = "토요일 평시 배차 간격(분)", example = "")
    private Integer satNpeekAlloc;

    @Schema(description = "일요일 출퇴근 배차 간격(분)", example = "")
    private Integer sunPeekAlloc;

    @Schema(description = "일요일 평시 배차 간격(분)", example = "")
    private Integer sunNpeekAlloc;

    @Schema(description = "주말 출퇴근 배차 간격(분)", example = "")
    private Integer wePeekAlloc;

    @Schema(description = "주말 평시 배차 간격(분)", example = "")
    private Integer weNpeekAlloc;

    @Schema(description = "상행 첫차 (HH:MM)", example = "05:30")
    private String upFirstTime;

    @Schema(description = "상행 막차 (HH:MM)", example = "23:00")
    private String upLastTime;

    @Schema(description = "하행 첫차 (HH:MM)", example = "05:30")
    private String downFirstTime;

    @Schema(description = "하행 막차 (HH:MM)", example = "23:00")
    private String downLastTime;

    @Schema(description = "저상버스 운행 여부 (Y/N, 판정 기준일 low_bus_base_dt)", example = "Y")
    private String lowBusYn;

    @Schema(description = "저상버스 운행 여부 판정 기준일", example = "2026-10-12")
    private LocalDate lowBusBaseDt;

    @Schema(description = "데이터 기준 일자", example = "2026-10-13")
    private LocalDate baseDt;
}
