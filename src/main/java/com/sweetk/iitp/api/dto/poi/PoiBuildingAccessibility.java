package com.sweetk.iitp.api.dto.poi;

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
@Schema(description = "건물 장애인편의시설")
public class PoiBuildingAccessibility {

    @Schema(description = "시설 고유 식별자", example = "1")
    private Integer faclId;

    @Schema(description = "원천 시설 정보 ID", example = "1234567")
    private String faclInfId;

    @Schema(description = "원천 복지시설 ID", example = "")
    private String wfcltId;

    @Schema(description = "시설명", example = "안양시청")
    private String faclName;

    @Schema(description = "시설 유형", example = "공공업무시설")
    private String faclType;

    @Schema(description = "주소", example = "경기도 안양시 동안구 시민대로 235")
    private String addr;

    @Schema(description = "위도 (WGS84)", example = "37.3943")
    private Double latitude;

    @Schema(description = "경도 (WGS84)", example = "126.9568")
    private Double longitude;

    @Schema(description = "설립(사용승인) 일자", example = "19961201")
    private String estbDate;

    @Schema(description = "승강기 설치 여부 (Y/N, 미확인 null)", example = "Y")
    private String elevatorYn;

    @Schema(description = "장애인 화장실 설치 여부 (Y/N, 미확인 null)", example = "Y")
    private String disToiletYn;

    @Schema(description = "장애인 전용 주차구역 설치 여부 (Y/N, 미확인 null)", example = "Y")
    private String disParkingYn;

    @Schema(description = "주출입구 높이차이 제거(경사로) 여부 (Y/N, 미확인 null)", example = "Y")
    private String entranceRampYn;

    @Schema(description = "주출입구(문) 편의시설 여부 (Y/N, 미확인 null)", example = "Y")
    private String entranceDoorYn;

    @Schema(description = "주출입구 접근로 편의시설 여부 (Y/N, 미확인 null)", example = "Y")
    private String approachRoadYn;

    @Schema(description = "유도 및 안내 설비 여부 (Y/N, 미확인 null)", example = "N")
    private String guideFacilityYn;

    @Schema(description = "장애인 사용 가능 객실 여부 (숙박시설, 미확인 null)", example = "")
    private String accessibleRoomYn;

    @Schema(description = "설치된 편의시설 항목(원천 원문)", example = "장애인전용주차구역, 주출입구 높이차이 제거, 주출입구 접근로")
    private String evalInfo;

    @Schema(description = "데이터 기준 일자", example = "2026-10-13")
    private LocalDate baseDt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "요청 위치로부터 거리(미터). 위치 기반 검색에서만 제공", example = "420")
    private Integer distance;
}
