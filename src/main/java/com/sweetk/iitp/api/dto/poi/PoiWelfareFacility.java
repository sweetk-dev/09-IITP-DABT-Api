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
@Schema(description = "장애인복지시설 (경기도 — 거주시설·단기거주시설·보호작업장·자립생활지원센터·관련 기관)")
public class PoiWelfareFacility {

    @Schema(description = "시설 고유 식별자", example = "1")
    private Integer facilityId;

    @Schema(description = "시설 종류 코드 [residence:장애유형별 거주시설, short_residence:단기거주시설, protect_workshop:보호작업장, il_center:중증장애인자립생활지원센터, support_center:장애인 관련 시설]", example = "residence")
    private String facilityType;

    @Schema(description = "시설 종류명", example = "장애유형별 거주시설")
    private String facilityTypeName;

    @Schema(description = "세부 구분 (거주시설: 장애유형, 관련 시설: 기관 구분)", example = "지적장애인시설")
    private String subType;

    @Schema(description = "시도 코드 (7자리)", example = "9410000")
    private String sidoCode;

    @Schema(description = "시군 코드", example = "41170")
    private String sigunCd;

    @Schema(description = "시군명", example = "안양시")
    private String sigunName;

    @Schema(description = "시설명", example = "밀알장애인자립생활센터")
    private String name;

    @Schema(description = "도로명 주소", example = "경기도 안양시 동안구 평촌대로 358")
    private String addrRoad;

    @Schema(description = "지번 주소", example = "경기도 안양시 동안구 비산동 1033번지")
    private String addrJibun;

    @Schema(description = "우편번호", example = "13941")
    private String zipCode;

    @Schema(description = "위도 (WGS84)", example = "37.402715")
    private Double latitude;

    @Schema(description = "경도 (WGS84)", example = "126.9500835")
    private Double longitude;

    @Schema(description = "전화번호", example = "031-000-0000")
    private String tel;

    @Schema(description = "홈페이지", example = "http://www.example.org/")
    private String homepage;

    @Schema(description = "정원 (거주·단기거주: 입소 정원, 보호작업장: 근로장애인 정원)", example = "53")
    private Integer capacityCnt;

    @Schema(description = "현원 (거주·단기거주: 입소 현원, 보호작업장: 근로장애인 현원)", example = "49")
    private Integer currentCnt;

    @Schema(description = "종사자 정원", example = "49")
    private Integer staffCapacityCnt;

    @Schema(description = "종사자 현원", example = "44")
    private Integer staffCurrentCnt;

    @Schema(description = "설치 신고일", example = "2001-11-23")
    private LocalDate installReportDt;

    @Schema(description = "운영 법인명", example = "무지개동산")
    private String corpName;

    @Schema(description = "원천 데이터", example = "경기도 장애인복지시설 현황(경기데이터드림)")
    private String sourceName;

    @Schema(description = "원천과 대조해 확인한 날 (원천이 기준일을 따로 주지 않는다)", example = "2026-10-15")
    private LocalDate checkedDt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "요청 위치로부터 거리(미터). 위치 기반 검색에서만 제공", example = "420")
    private Integer distance;
}
