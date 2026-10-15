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
@Schema(description = "장애인 문화·체육 시설")
public class PoiCultureSportsFacility {
    @Schema(description = "시설 고유 식별자", example = "1")
    @JsonProperty("facility_id")
    private Integer facilityId;

    @Schema(description = "시설 종류 코드 [braille_lib:점자도서관, booknarae_lib:책나래서비스 제공도서관, sports_fac:장애인체육시설, sports_org:장애인체육회]",
            example = "braille_lib",
            allowableValues = {"braille_lib", "booknarae_lib", "sports_fac", "sports_org"})
    @JsonProperty("facility_type")
    private String facilityType;

    @Schema(description = "시설 종류명", example = "점자도서관")
    @JsonProperty("facility_type_name")
    private String facilityTypeName;

    @Schema(description = "시도 코드 (7자리)", example = "9410000")
    @JsonProperty("sido_code")
    private String sidoCode;

    @Schema(description = "원천 시군 코드", example = "41170")
    @JsonProperty("sigun_cd")
    private String sigunCd;

    @Schema(description = "원천 시군명 (원천 값 그대로이며 주소의 시군과 다를 수 있음)", example = "안양시")
    @JsonProperty("sigun_nm")
    private String sigunNm;

    @Schema(description = "기관·시설·단체명", example = "안양시립석수도서관")
    @JsonProperty("name")
    private String name;

    @Schema(description = "도로명 주소", example = "경기도 안양시 만안구 ...")
    @JsonProperty("addr_road")
    private String addrRoad;

    @Schema(description = "지번 주소", example = "경기도 안양시 만안구 ...")
    @JsonProperty("addr_jibun")
    private String addrJibun;

    @Schema(description = "우편번호", example = "14082")
    @JsonProperty("zip_code")
    private String zipCode;

    @Schema(description = "위도 (WGS84)", example = "37.4017")
    @JsonProperty("latitude")
    private Double latitude;

    @Schema(description = "경도 (WGS84)", example = "126.9227")
    @JsonProperty("longitude")
    private Double longitude;

    @Schema(description = "대표 전화", example = "031-000-0000")
    @JsonProperty("tel")
    private String tel;

    @Schema(description = "홈페이지", example = "https://www.example.or.kr")
    @JsonProperty("homepage")
    private String homepage;

    @Schema(description = "설치신고일 (점자도서관·장애인체육시설만 제공)", example = "2001-05-10")
    @JsonProperty("install_report_dt")
    private LocalDate installReportDt;

    @Schema(description = "운영 법인명 (점자도서관만 제공)", example = "사회복지법인 ...")
    @JsonProperty("corp_name")
    private String corpName;

    @Schema(description = "데이터 기준 일자 (마지막 수집 반영일)", example = "2026-10-14")
    @JsonProperty("base_dt")
    private LocalDate baseDt;
}
