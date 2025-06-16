package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.MvPoiCategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Schema(description = "POI 검색 요청")
public class MvPoiSearchCatReq {
//    @Schema(description = "언어권 (선택사항)", example = "ko")
//    private String lang = "ko";

    @Schema(description = "카테고리 타입(옵션)", example = "restaurant")
    private MvPoiCategoryType catType;

    @Schema(description = "catType의 서브 카테고리 타입(옵션)", example = "한식, 카페")
    private MvPoiCategoryType subCateType;

    @Schema(description = "검색어 (옵션) (단독 세팅도 가능)", example = "서대문형무소역사관")
    private String name;
} 