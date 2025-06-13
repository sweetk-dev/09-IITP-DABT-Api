package com.sweetk.iitp.api.dto.poi;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Schema(description = "POI 검색 요청")
public class MvPoiSearchLocReq {
//    @Schema(description = "언어권 (선택사항)", example = "ko")
//    private String lang = "ko";

    @Schema(description = "위도 (searchType이 location일 때만 사용)", example = "37.5665")
    @Min(value = -90, message = "Latitude must be between -90 and 90")
    @Max(value = 90, message = "Latitude must be between -90 and 90")
    private Double latitude;

    @Schema(description = "경도 (searchType이 location일 때만 사용)", example = "126.9780")
    @Min(value = -180, message = "Longitude must be between -180 and 180")
    @Max(value = 180, message = "Longitude must be between -180 and 180")
    private Double longitude;

    @Schema(description = "검색 반경(미터) (searchType이 location일 때만 사용)", example = "1000")
    @Min(value = 0, message = "Radius must be positive")
    private Double radius;
} 