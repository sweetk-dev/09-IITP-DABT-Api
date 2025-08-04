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
@Schema(description = "공중 화장실 카테고리 검색 요청")
public class PoiPublicToiletInfoSearchCatReq {
    
    @Schema(description = "화장실명 검색 (최소 2글자)", example = "서울역")
    @Size(min = 2, message = "화장실명은 최소 2글자 이상이어야 합니다")
    private String toiletName;
    
    @Schema(description = "시도 코드 (7자리)", example = "1100000")
    @Pattern(regexp = "^\\d{7}$", message = "시도 코드는 7자리 숫자여야 합니다")
    private String sidoCode;
    
    @Schema(description = "화장실 유형", example = "공중화장실", 
            allowableValues = {"공중화장실", "관광화장실", "기타"})
    private String toiletType;
    
    @Schema(description = "장애인 시설 보유 여부", example = "Y", 
            allowableValues = {"Y", "N"})
    private String disabilityFacilityYn;
    
    @Schema(description = "24시간 개방 여부", example = "Y", 
            allowableValues = {"Y", "N"})
    private String open24hYn;
    
    public void setToiletName(String toiletName) {
        this.toiletName = (toiletName != null && toiletName.trim().isEmpty()) ? null : toiletName;
    }
} 