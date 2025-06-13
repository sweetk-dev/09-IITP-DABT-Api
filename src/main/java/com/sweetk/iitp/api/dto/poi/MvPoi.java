package com.sweetk.iitp.api.dto.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@Schema(description = "이동현 POI 정보")
public class MvPoi {
    @Schema(description = "POI 고유 식별자", example = "1")
    @JsonProperty("poi_id")
    private Long poiId;

    @Schema(description = "언어 코드", example = "ko")
    @JsonProperty("language_code")
    private String languageCode;

    @Schema(description = "POI 제목", example = "서대문형무소역사관")
    @JsonProperty("title")
    private String title;

    @Schema(description = "POI 요약 정보", example = "[2021 유니버설 관광시설 인증] <br>\n" + "잔혹한 근대사가 역사가 고스란히 남아 있는 역사관")
    @JsonProperty("summary")
    private String summary;

    @Schema(description = "POI 기본 정보", example = "[2021 유니버설 관광시설 인증][개 요]서대문형무소역사관은 구 서울구치소 시설을 개조하여 과거 경성감옥과서대문감옥을 복원한 독립운동 및 민주화운동 관련 역사관이다....")
    @JsonProperty("basic_info")
    private String basicInfo;

    @Schema(description = "주소 코드", example = "03732")
    @JsonProperty("address_code")
    private String addressCode;

    @Schema(description = "도로명 주소", example = "서울 서대문구 통일로 251 (현저동, 독립공원)")
    @JsonProperty("address_road")
    private String addressRoad;

    @Schema(description = "상세 주소", example = "")
    @JsonProperty("address_detail")
    private String addressDetail;

    @Schema(description = "위도", example = "37.57460779")
    @JsonProperty("latitude")
    private BigDecimal latitude;

    @Schema(description = "경도", example = "126.95561110")
    @JsonProperty("longitude")
    private BigDecimal longitude;

    @Schema(description = "상세 정보 JSON", example = "{\"phone\": \"02-360-8590\", \"website\": \"https://sphh.sscmc.or.kr\", ...}")
    @JsonProperty("detail_json")
    private String detailJson;

    @Schema(description = "검색 필터 JSON", example = "{\"search_filter\": {\"tourist_spot\": \"역사 · 고궁 · 문화재,전시 · 공연 · 관람,체험 · 공예\", \"tourist_type\": \"고령자,보행장애,시각장애,영유아 및 동반자,청각장애\", \"accessibility_facilities\": \"기저귀 교환대 있음,장애인 주차장 있음,....\"}}")
    @JsonProperty("search_filter_json")
    private String searchFilterJson;
}
