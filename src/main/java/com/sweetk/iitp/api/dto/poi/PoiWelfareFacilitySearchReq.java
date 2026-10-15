package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.PoiWelfareFacilityType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "장애인복지시설 검색 조건 (모두 선택)")
public class PoiWelfareFacilitySearchReq {

    @Schema(description = "시설 종류 [residence:장애유형별 거주시설, short_residence:단기거주시설, protect_workshop:보호작업장, il_center:중증장애인자립생활지원센터, support_center:장애인 관련 시설]",
            example = "residence", allowableValues = {"residence", "short_residence", "protect_workshop", "il_center", "support_center"})
    private PoiWelfareFacilityType facilityType;

    @Schema(description = "시군명 (부분 일치, 최소 2글자)", example = "안양")
    @Size(min = 2, message = "시군명은 최소 2글자 이상이어야 합니다")
    private String sigunName;

    @Schema(description = "세부 구분 (부분 일치, 최소 2글자) — 거주시설 장애유형, 관련 시설 기관 구분", example = "지적")
    @Size(min = 2, message = "세부 구분은 최소 2글자 이상이어야 합니다")
    private String subType;

    @Schema(description = "시설명 (부분 일치, 최소 2글자)", example = "자립생활")
    @Size(min = 2, message = "시설명은 최소 2글자 이상이어야 합니다")
    private String name;
}
