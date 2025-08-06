package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.MvPoiCategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(description = "POI 검색 요청")
public class MvPoiSearchLocReq extends PoiBaseSearchLocReq {
//    @Schema(description = "언어권 (선택사항)", example = "ko")
//    private String lang = "ko";

    @Schema(description = "카테고리",
            example = "restaurant",
            allowableValues = {"tourist_spot", "restaurant", "shopping", "accommodation"})
    private MvPoiCategoryType category;

    @Schema(description = "이름, 최소 2글자 이상", example = "서대문형무소역사관")
    @Size(min = 2)
    private String name;

} 