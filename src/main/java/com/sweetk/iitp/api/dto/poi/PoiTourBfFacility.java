package com.sweetk.iitp.api.dto.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PoiTourBfFacility {
    @Schema(description = "무장애 관광지 시설 고유 식별자", example = "1")
    @JsonProperty("fclt_id")
    private Integer fcltId;

    @Schema(description = "시도 코드", example = "11")
    @JsonProperty("sido_code")
    private String sidoCode;

    @Schema(description = "무장애 관광지명", example = "서울타워")
    @JsonProperty("fclt_name")
    private String fcltName;

    @Schema(description = "장애인 화장실 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("toilet_yn")
    private String toiletYn;

    @Schema(description = "엘리베이터 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("elevator_yn")
    private String elevatorYn;

    @Schema(description = "장애인 주차장 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("parking_yn")
    private String parkingYn;

    @Schema(description = "경사로 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("slope_yn")
    private String slopeYn;

    @Schema(description = "지하철 접근성 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("subway_yn")
    private String subwayYn;

    @Schema(description = "버스 정류장 접근성 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("bus_stop_yn")
    private String busStopYn;

    @Schema(description = "휠체어 대여 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("wheelchair_rent_yn")
    private String wheelchairRentYn;

    @Schema(description = "촉지도식 안내판 설치 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("tactile_map_yn")
    private String tactileMapYn;

    @Schema(description = "오디오 가이드 제공 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("audio_guide_yn")
    private String audioGuideYn;

    @Schema(description = "아기 돌봄/수유실 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("nursing_room_yn")
    private String nursingRoomYn;

    @Schema(description = "무장애 객실 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("accessible_room_yn")
    private String accessibleRoomYn;

    @Schema(description = "유아차 대여 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("stroller_rent_yn")
    private String strollerRentYn;

    @Schema(description = "소재지 도로명 주소", example = "서울특별시 용산구 남산공원길 105")
    @JsonProperty("addr_road")
    private String addrRoad;

    @Schema(description = "소재지 지번 주소", example = "서울특별시 용산구 용산동2가 산1-3")
    @JsonProperty("addr_jibun")
    private String addrJibun;

    @Schema(description = "위도", example = "37.551169")
    @JsonProperty("latitude")
    private Double latitude;

    @Schema(description = "경도", example = "126.988226")
    @JsonProperty("longitude")
    private Double longitude;

    @Schema(description = "데이터 기준 일자", example = "2023-12-01")
    @JsonProperty("base_dt")
    private LocalDate baseDt;
} 