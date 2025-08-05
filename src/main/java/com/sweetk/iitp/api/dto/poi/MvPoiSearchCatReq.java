package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.MvPoiCategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Schema(description = "POI 검색 요청")
public class MvPoiSearchCatReq {
//    @Schema(description = "언어권 (선택사항)", example = "ko")
//    private String lang = "ko";

    @Schema(description = "카테고리 타입(옵션)",
            example = "restaurant",
            allowableValues = {"tourist_spot", "restaurant", "shopping", "accommodation"})
    private MvPoiCategoryType category;

    @Schema(description = "catType의 서브 카테고리 타입(옵션), 여러개 카테고리 설정 가능 (','로 구분) 최소 2글자 이상", example = "한식, 카페")
    @Size(min = 2)
    private String subCate;

    @Schema(description = "검색어 (옵션) (단독 세팅도 가능), 최소 2글자 이상", example = "서대문형무소역사관")
    @Size(min = 2)
    private String name;

    public void setName(String name) {
        this.name = (name != null && name.trim().isEmpty()) ? null : name;
    }

    public void setSubCat(String subCategory) {
        if( subCategory == null && subCategory.trim().isEmpty()) {
            this.subCate = null;
            return;
        }

        if( this.category != null ) {
            this.subCate = subCategory;
        }
    }
} 