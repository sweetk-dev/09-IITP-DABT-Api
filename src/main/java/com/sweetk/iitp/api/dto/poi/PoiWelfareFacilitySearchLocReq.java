package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.PoiWelfareFacilityType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "장애인복지시설 위치 기반 검색 요청")
public class PoiWelfareFacilitySearchLocReq extends PoiBaseSearchLocReq {

    @Schema(description = "시설 종류 [residence:장애유형별 거주시설, short_residence:단기거주시설, protect_workshop:보호작업장, il_center:중증장애인자립생활지원센터, support_center:장애인 관련 시설]",
            example = "il_center", allowableValues = {"residence", "short_residence", "protect_workshop", "il_center", "support_center"})
    private PoiWelfareFacilityType facilityType;
}
