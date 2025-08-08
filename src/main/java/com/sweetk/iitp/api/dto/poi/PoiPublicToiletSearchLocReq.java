package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.PoiPublicToiletType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "공중 화장실 위치 기반 검색 요청")
public class PoiPublicToiletSearchLocReq extends PoiBaseSearchLocReq {

    @Schema(description = "화장실명 검색 (최소 2글자)", example = "서울역")
    @Size(min = 2, message = "화장실명은 최소 2글자 이상이어야 합니다")
    private String toiletName;

    @Schema(description = "화장실 유형 [ PUBLIC:공중화장실, SIMPLE:간이화장실, OPEN:개방화장실, MOBILE:이동화장실]",
            example = "PUBLIC",
            allowableValues = {"PUBLIC","SIMPLE", "OPEN", "MOBILE"})
    private PoiPublicToiletType toiletType;

//    @Schema(description = "장애인 시설 보유 여부", example = "Y",
//            allowableValues = {"Y", "N"})
//    private String disabilityFacilityYn;

    @Schema(description = "24시간 개방 여부", example = "Y",
            allowableValues = {"Y", "N"})
    private String open24hYn;
} 