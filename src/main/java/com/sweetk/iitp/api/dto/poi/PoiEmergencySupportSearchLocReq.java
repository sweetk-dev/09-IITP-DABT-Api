package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.PoiEmergencySupportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "긴급대응 지원 거점 위치 기반 검색 요청")
public class PoiEmergencySupportSearchLocReq extends PoiBaseSearchLocReq {

    @Schema(description = "거점 종류 [charge:전동보장구 급속충전기, repair:보조기기 수리·지원 센터, calltaxi:교통약자 이동지원센터]",
            example = "charge", allowableValues = {"charge", "repair", "calltaxi"})
    private PoiEmergencySupportType supportType;
}
