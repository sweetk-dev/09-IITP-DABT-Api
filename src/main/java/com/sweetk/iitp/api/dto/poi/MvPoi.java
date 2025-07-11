package com.sweetk.iitp.api.dto.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record MvPoi(
//    @Schema(description = "POI 고유 식별자", example = "1")
//    @JsonProperty("poi_id")
//    Long poiId,

    @Schema(description = "언어 코드", example = "ko")
    @JsonProperty("language_code")
    String languageCode,

    @Schema(description = "카테고리",
            example = "tourist_spot",
            allowableValues = {"tourist_spot", "restaurant", "shopping", "accommodation"})
    @JsonProperty("category")
    String category,

    @Schema(description = "서브 카테고리", example = "역사 · 고궁 · 문화재")
    @JsonProperty("sub_category")
    String subCategory,

    @Schema(description = "POI 제목", example = "서대문형무소역사관")
    @JsonProperty("title")
    String title,

    @Schema(description = "POI 요약 정보", example = "[2021 유니버설 관광시설 인증] <br>\n" + "잔혹한 근대사가 역사가 고스란히 남아 있는 역사관")
    @JsonProperty("summary")
    String summary,

    @Schema(description = "POI 기본 정보", example = "[2021 유니버설 관광시설 인증][개 요]서대문형무소역사관은 구 서울구치소 시설을 개조하여 과거 경성감옥과서대문감옥을 복원한 독립운동 및 민주화운동 관련 역사관이다....")
    @JsonProperty("basic_info")
    String basicInfo,

    @Schema(description = "주소 코드", example = "03732")
    @JsonProperty("address_code")
    String addressCode,

    @Schema(description = "도로명 주소", example = "서울 서대문구 통일로 251 (현저동, 독립공원)")
    @JsonProperty("address_road")
    String addressRoad,

    @Schema(description = "상세 주소", example = "")
    @JsonProperty("address_detail")
    String addressDetail,

    @Schema(description = "위도", example = "37.57460779")
    @JsonProperty("latitude")
    BigDecimal latitude,

    @Schema(description = "경도", example = "126.9572029")
    @JsonProperty("longitude")
    BigDecimal longitude,

    @Schema(description = "상세 정보 JSON", example = "{}")
    @JsonProperty("detail_json")
    String detailJson,

    @Schema(description = "검색 필터 JSON", example = "{}")
    @JsonProperty("search_filter_json")
    String searchFilterJson
) {}
