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
public class PoiPublicToiletInfo {
    @Schema(description = "공중 화장실 고유 식별자", example = "1")
    @JsonProperty("toilet_id")
    private Integer toiletId;

    @Schema(description = "시도 코드", example = "11")
    @JsonProperty("sido_code")
    private String sidoCode;

    @Schema(description = "화장실명", example = "서울역 공중화장실")
    @JsonProperty("toilet_name")
    private String toiletName;

    @Schema(description = "구분", example = "공중화장실")
    @JsonProperty("toilet_type")
    private String toiletType;

    @Schema(description = "설치 근거", example = "공중화장실 등에 관한 법률")
    @JsonProperty("basis")
    private String basis;

    @Schema(description = "소재지 도로명 주소", example = "서울특별시 용산구 한강대로 405")
    @JsonProperty("addr_road")
    private String addrRoad;

    @Schema(description = "소재지 지번 주소", example = "서울특별시 용산구 한강로3가 40-999")
    @JsonProperty("addr_jibun")
    private String addrJibun;

    @Schema(description = "남성용 대변기 수", example = "3")
    @JsonProperty("m_toilet_count")
    private Integer mToiletCount;

    @Schema(description = "남성용 소변기 수", example = "2")
    @JsonProperty("m_urinal_count")
    private Integer mUrinalCount;

    @Schema(description = "남성용 장애인 대변기 수", example = "1")
    @JsonProperty("m_dis_toilet_count")
    private Integer mDisToiletCount;

    @Schema(description = "남성용 장애인 소변기 수", example = "1")
    @JsonProperty("m_dis_urinal_count")
    private Integer mDisUrinalCount;

    @Schema(description = "남성용 어린이 대변기 수", example = "1")
    @JsonProperty("m_child_toilet_count")
    private Integer mChildToiletCount;

    @Schema(description = "남성용 어린이 소변기 수", example = "1")
    @JsonProperty("m_child_urinal_count")
    private Integer mChildUrinalCount;

    @Schema(description = "여성용 대변기 수", example = "4")
    @JsonProperty("f_toilet_count")
    private Integer fToiletCount;

    @Schema(description = "여성용 장애인 대변기 수", example = "1")
    @JsonProperty("f_dis_toilet_count")
    private Integer fDisToiletCount;

    @Schema(description = "여성용 어린이 대변기 수", example = "1")
    @JsonProperty("f_child_toilet_count")
    private Integer fChildToiletCount;

    @Schema(description = "관리기관명", example = "서울특별시 용산구청")
    @JsonProperty("managing_org")
    private String managingOrg;

    @Schema(description = "전화번호", example = "02-2199-6114")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Schema(description = "개방시간", example = "24시간")
    @JsonProperty("open_time")
    private String openTime;

    @Schema(description = "개방시간 상세", example = "연중무휴 24시간 개방")
    @JsonProperty("open_time_detail")
    private String openTimeDetail;

    @Schema(description = "설치 연월", example = "2020-01")
    @JsonProperty("install_dt")
    private String installDt;

    @Schema(description = "위도", example = "37.554678")
    @JsonProperty("latitude")
    private Double latitude;

    @Schema(description = "경도", example = "126.970606")
    @JsonProperty("longitude")
    private Double longitude;

    @Schema(description = "화장실 소유 구분", example = "공공기관-지방자치단체")
    @JsonProperty("owner_type")
    private String ownerType;

    @Schema(description = "오물 처리 방식", example = "수거식")
    @JsonProperty("waste_process_type")
    private String wasteProcessType;

    @Schema(description = "안전관리시설설치대상여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("safety_target_yn")
    private String safetyTargetYn;

    @Schema(description = "비상벨 설치 여부", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("emg_bell_yn")
    private String emgBellYn;

    @Schema(description = "비상벨 설치 장소", example = "화장실 내부")
    @JsonProperty("emg_bell_location")
    private String emgBellLocation;

    @Schema(description = "화장실 입구 CCTV 설치 유무", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("cctv_yn")
    private String cctvYn;

    @Schema(description = "기저귀 교환대 유무", example = "Y", allowableValues = {"Y", "N"})
    @JsonProperty("diaper_table_yn")
    private String diaperTableYn;

    @Schema(description = "기저귀 교환대 장소", example = "여성 화장실 내부")
    @JsonProperty("diaper_table_location")
    private String diaperTableLocation;

    @Schema(description = "리모델링 연월", example = "2023-06-15")
    @JsonProperty("remodeled_dt")
    private String remodeledDt;

    @Schema(description = "데이터 기준 일자", example = "2023-12-01")
    @JsonProperty("base_dt")
    private LocalDate baseDt;
} 