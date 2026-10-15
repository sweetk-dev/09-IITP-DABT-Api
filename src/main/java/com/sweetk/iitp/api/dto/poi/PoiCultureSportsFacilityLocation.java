package com.sweetk.iitp.api.dto.poi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "장애인 문화·체육 시설 (요청 위치로부터의 거리 포함)")
public class PoiCultureSportsFacilityLocation extends PoiCultureSportsFacility {
    @Schema(description = "요청한 위치로부터 거리(미터)", example = "850")
    @JsonProperty("distance")
    private Integer distance;

    public PoiCultureSportsFacilityLocation(PoiCultureSportsFacility f, Integer distance) {
        super(f.getFacilityId(), f.getFacilityType(), f.getFacilityTypeName(), f.getSidoCode(),
              f.getSigunCd(), f.getSigunNm(), f.getName(), f.getAddrRoad(), f.getAddrJibun(),
              f.getZipCode(), f.getLatitude(), f.getLongitude(), f.getTel(), f.getHomepage(),
              f.getInstallReportDt(), f.getCorpName(), f.getBaseDt());
        this.distance = distance;
    }
}
