package com.sweetk.iitp.api.dto.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record PoiSubwayElevator(
    @Schema(description = "지하철 엘리베이터 고유 식별자", example = "1")
    @JsonProperty("subway_id")
    Integer subwayId,

    @Schema(description = "시도 코드", example = "11")
    @JsonProperty("sido_code")
    String sidoCode,

    @Schema(description = "노드링크 유형", example = "NODE")
    @JsonProperty("node_link_type")
    String nodeLinkType,

    @Schema(description = "WKT 기반 위치정보", example = "POINT(126.9572029 37.57460779)")
    @JsonProperty("node_wkt")
    String nodeWkt,

    @Schema(description = "노드 ID", example = "123456789")
    @JsonProperty("node_id")
    Long nodeId,

    @Schema(description = "노드 유형 코드", example = "1", allowableValues = {"0", "1", "2", "3"})
    @JsonProperty("node_type_code")
    Integer nodeTypeCode,

    @Schema(description = "노드 유형 이름", example = "지하철 출입구")
    @JsonProperty("node_type_name")
    String nodeTypeName,

    @Schema(description = "시군구 코드", example = "11110")
    @JsonProperty("sigungu_code")
    String sigunguCode,

    @Schema(description = "시군구 명", example = "종로구")
    @JsonProperty("sigungu_name")
    String sigunguName,

    @Schema(description = "읍면동 코드", example = "1111010100")
    @JsonProperty("eupmyeondong_code")
    String eupmyeondongCode,

    @Schema(description = "읍면동 명", example = "종로2가")
    @JsonProperty("eupmyeondong_name")
    String eupmyeondongName,

    @Schema(description = "지하철역 코드", example = "267")
    @JsonProperty("station_code")
    String stationCode,

    @Schema(description = "지하철역 명", example = "혜화")
    @JsonProperty("station_name")
    String stationName,

    @Schema(description = "위도", example = "37.57460779")
    @JsonProperty("latitude")
    Double latitude,

    @Schema(description = "경도", example = "126.9572029")
    @JsonProperty("longitude")
    Double longitude,

    @Schema(description = "데이터 기준 일자", example = "20231201")
    @JsonProperty("base_dt")
    String baseDt
) {} 