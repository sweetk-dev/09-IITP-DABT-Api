package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.PoiCultureSportsType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "장애인 문화·체육 시설 위치 기반 검색 요청")
public class PoiCultureSportsFacilitySearchLocReq extends PoiBaseSearchLocReq {

    @Schema(description = "시설 종류 [braille_lib:점자도서관, booknarae_lib:책나래서비스 제공도서관, sports_fac:장애인체육시설, sports_org:장애인체육회]",
            example = "booknarae_lib",
            allowableValues = {"braille_lib", "booknarae_lib", "sports_fac", "sports_org"})
    private PoiCultureSportsType facilityType;

    @Schema(description = "기관·시설·단체명 검색 (부분 일치, 최소 2글자)", example = "도서관")
    @Size(min = 2, message = "이름은 최소 2글자 이상이어야 합니다")
    private String name;

    public void setName(String name) {
        this.name = (name != null && name.trim().isEmpty()) ? null : name;
    }
}
