package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.PoiCultureSportsType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "장애인 문화·체육 시설 카테고리 검색 요청 (조건 하나 이상 필요)")
public class PoiCultureSportsFacilitySearchCatReq {

    @Schema(description = "시설 종류 [braille_lib:점자도서관, booknarae_lib:책나래서비스 제공도서관, sports_fac:장애인체육시설, sports_org:장애인체육회]",
            example = "braille_lib",
            allowableValues = {"braille_lib", "booknarae_lib", "sports_fac", "sports_org"})
    private PoiCultureSportsType facilityType;

    @Schema(description = "기관·시설·단체명 검색 (부분 일치, 최소 2글자)", example = "도서관")
    @Size(min = 2, message = "이름은 최소 2글자 이상이어야 합니다")
    private String name;

    @Schema(description = "시군명 검색 (부분 일치, 최소 2글자)", example = "안양")
    @Size(min = 2, message = "시군명은 최소 2글자 이상이어야 합니다")
    private String sigunNm;

    @Schema(description = "시도 코드 (7자리)", example = "9410000")
    @Pattern(regexp = "^\\d{7}$", message = "시도 코드는 7자리 숫자여야 합니다")
    private String sidoCode;

    public void setName(String name) {
        this.name = (name != null && name.trim().isEmpty()) ? null : name;
    }

    public void setSigunNm(String sigunNm) {
        this.sigunNm = (sigunNm != null && sigunNm.trim().isEmpty()) ? null : sigunNm;
    }

    public void setSidoCode(String sidoCode) {
        this.sidoCode = (sidoCode != null && sidoCode.trim().isEmpty()) ? null : sidoCode;
    }

    public boolean hasCondition() {
        return facilityType != null || name != null || sigunNm != null || sidoCode != null;
    }
}
