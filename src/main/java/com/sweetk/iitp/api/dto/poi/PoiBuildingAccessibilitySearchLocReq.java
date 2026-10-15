package com.sweetk.iitp.api.dto.poi;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "건물 장애인편의시설 위치 기반 검색 요청")
public class PoiBuildingAccessibilitySearchLocReq extends PoiBaseSearchLocReq {

    @Schema(description = "시설 유형 (부분 일치, 최소 2글자)", example = "업무시설")
    @Size(min = 2, message = "시설 유형은 최소 2글자 이상이어야 합니다")
    private String faclType;

    @Schema(description = "승강기 설치 여부", allowableValues = {"Y", "N"})
    @Pattern(regexp = "^[YN]$", message = "Y 또는 N")
    private String elevatorYn;

    @Schema(description = "장애인 화장실 설치 여부", allowableValues = {"Y", "N"})
    @Pattern(regexp = "^[YN]$", message = "Y 또는 N")
    private String disToiletYn;

    @Schema(description = "장애인 전용 주차구역 설치 여부", allowableValues = {"Y", "N"})
    @Pattern(regexp = "^[YN]$", message = "Y 또는 N")
    private String disParkingYn;
}
