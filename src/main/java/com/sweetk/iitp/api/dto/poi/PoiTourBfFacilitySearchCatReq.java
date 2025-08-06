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
@Schema(description = "무장애 관광지 시설 카테고리 검색 요청")
public class PoiTourBfFacilitySearchCatReq {
    
    @Schema(description = "시설명 검색 (최소 2글자)", example = "서울타워")
    @Size(min = 2, message = "시설명은 최소 2글자 이상이어야 합니다")
    private String fcltName;
    
    @Schema(description = "시도 코드 (7자리)", example = "9110000")
    @Pattern(regexp = "^\\d{7}$", message = "시도 코드는 7자리 숫자여야 합니다")
    private String sidoCode;
    
    @Schema(description = "장애인 화장실 여부", example = "Y", 
            allowableValues = {"Y", "N"})
    private String toiletYn;
    
    @Schema(description = "엘리베이터 여부", example = "Y", 
            allowableValues = {"Y", "N"})
    private String elevatorYn;
    
    @Schema(description = "장애인 주차장 여부", example = "Y", 
            allowableValues = {"Y", "N"})
    private String parkingYn;
    
    @Schema(description = "휠체어 대여 여부", example = "Y", 
            allowableValues = {"Y", "N"})
    private String wheelchairRentYn;
    
    @Schema(description = "촉지도식 안내판 설치 여부", example = "Y", 
            allowableValues = {"Y", "N"})
    private String tactileMapYn;
    
    @Schema(description = "오디오 가이드 제공 여부", example = "Y", 
            allowableValues = {"Y", "N"})
    private String audioGuideYn;
    
    public void setFcltName(String fcltName) {
        this.fcltName = (fcltName != null && fcltName.trim().isEmpty()) ? null : fcltName;
    }
} 