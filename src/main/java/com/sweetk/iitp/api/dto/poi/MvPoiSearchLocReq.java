package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.MvPoiCategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@Schema(description = "POI 검색 요청")
public class MvPoiSearchLocReq {
//    @Schema(description = "언어권 (선택사항)", example = "ko")
//    private String lang = "ko";

    @Schema(description = "카테고리 타입(옵션)",
            example = "restaurant",
            allowableValues = {"tourist_spot", "restaurant", "shopping", "accommodation"})
    private MvPoiCategoryType category;

    @Schema(description = "검색어 (옵션) (단독 세팅도 가능), 최소 2글자 이상", example = "서대문형무소역사관")
    @Size(min = 2)
    private String name;

    @NotNull
    @Schema(description = "위도 (searchType이 location일 때만 사용)", example = "37.5665")
    @Min(value = -90, message = "Latitude must be between -90 and 90")
    @Max(value = 90, message = "Latitude must be between -90 and 90")
    private BigDecimal latitude;

    @NotNull
    @Schema(description = "경도 (searchType이 location일 때만 사용)", example = "126.9780")
    @Min(value = -180, message = "Longitude must be between -180 and 180")
    @Max(value = 180, message = "Longitude must be between -180 and 180")
    private BigDecimal longitude;

    @NotNull
    @Schema(description = "검색 반경(미터) (searchType이 location일 때만 사용)", example = "1000")
    @Min(value = 0, message = "Radius must be positive")
    private BigDecimal radius;
} 