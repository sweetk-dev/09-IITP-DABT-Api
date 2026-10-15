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
@Schema(description = "긴급대응 지원 거점 (전동보장구 충전기·보조기기 수리 센터·교통약자 이동지원센터)")
public class PoiEmergencySupport {

    @Schema(description = "거점 고유 식별자", example = "1")
    private Integer supportId;

    @Schema(description = "거점 종류 코드 [charge:전동보장구 급속충전기, repair:보조기기 수리·지원 센터, calltaxi:교통약자 이동지원센터]", example = "charge")
    private String supportType;

    @Schema(description = "거점 종류명", example = "전동보장구 급속충전기")
    private String supportTypeName;

    @Schema(description = "시도 코드 (7자리)", example = "9410000")
    private String sidoCode;

    @Schema(description = "시군구명", example = "안양시")
    private String sggName;

    @Schema(description = "거점명", example = "안양시청")
    private String name;

    @Schema(description = "설치 지점 설명 (같은 건물 안 복수 설치 구분, 충전기만)", example = "본관 1층 민원실 앞")
    private String installDesc;

    @Schema(description = "도로명 주소", example = "경기도 안양시 동안구 시민대로 235")
    private String addrRoad;

    @Schema(description = "지번 주소", example = "")
    private String addrJibun;

    @Schema(description = "우편번호", example = "")
    private String zipCode;

    @Schema(description = "위도 (WGS84)", example = "37.3943")
    private Double latitude;

    @Schema(description = "경도 (WGS84)", example = "126.9568")
    private Double longitude;

    @Schema(description = "전화번호 (충전기는 관리기관 번호일 수 있음)", example = "031-8045-2114")
    private String tel;

    @Schema(description = "홈페이지", example = "")
    private String homepage;

    @Schema(description = "운영 시간", example = "평일 09:00-18:00")
    private String openHours;

    @Schema(description = "비고 (관리기관·동시 사용 대수 등)", example = "동시사용 2대")
    private String note;

    @Schema(description = "원천 데이터", example = "전국전동휠체어급속충전기표준데이터")
    private String sourceName;

    @Schema(description = "원천 데이터 기준 일자", example = "2026-06-29")
    private LocalDate baseDt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "요청 위치로부터 거리(미터). 위치 기반 검색에서만 제공", example = "420")
    private Integer distance;
}
