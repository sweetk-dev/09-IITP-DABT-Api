package com.sweetk.iitp.api.dto.poi;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "건물 장애인편의시설 검색 조건 (모두 선택)")
public class PoiBuildingAccessibilitySearchReq {

    @Schema(description = "시설명 (부분 일치, 최소 2글자)", example = "시청")
    @Size(min = 2, message = "시설명은 최소 2글자 이상이어야 합니다")
    private String faclName;

    @Schema(description = "시설 유형 (부분 일치, 최소 2글자)", example = "업무시설")
    @Size(min = 2, message = "시설 유형은 최소 2글자 이상이어야 합니다")
    private String faclType;

    @Schema(description = "주소 (부분 일치, 최소 2글자)", example = "동안구")
    @Size(min = 2, message = "주소는 최소 2글자 이상이어야 합니다")
    private String addr;

    @Schema(description = "승강기 설치 여부", allowableValues = {"Y", "N"})
    @Pattern(regexp = "^[YN]$", message = "Y 또는 N")
    private String elevatorYn;

    @Schema(description = "장애인 화장실 설치 여부", allowableValues = {"Y", "N"})
    @Pattern(regexp = "^[YN]$", message = "Y 또는 N")
    private String disToiletYn;

    @Schema(description = "장애인 전용 주차구역 설치 여부", allowableValues = {"Y", "N"})
    @Pattern(regexp = "^[YN]$", message = "Y 또는 N")
    private String disParkingYn;

    @Schema(description = "주출입구 높이차이 제거(경사로) 여부", allowableValues = {"Y", "N"})
    @Pattern(regexp = "^[YN]$", message = "Y 또는 N")
    private String entranceRampYn;
}
