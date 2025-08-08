package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.PoiPublicToiletType;
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
public class PoiPublicToiletSearchCatReq {
    
    @Schema(description = "화장실명 검색 (최소 2글자)", example = "서울역")
    @Size(min = 2, message = "화장실명은 최소 2글자 이상이어야 합니다")
    private String toiletName;
    
    @Schema(description = "시도 코드 (7자리)", example = "91100000",
                allowableValues = {"9110000",
                                    "9260000","9270000",  "9280000","9290000",
                                    "9300000","9310000","9360000",
                                    "9410000", "9510000","9430000" ,"9440000",
                                    "9520000", "9460000", "9470000", "9480000","9500000"}
            )
    @Pattern(regexp = "^\\d{7}$", message = "시도 코드는 7자리 숫자여야 합니다")
    private String sidoCode;
    
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
    
    public void setToiletName(String toiletName) {
        this.toiletName = (toiletName != null && toiletName.trim().isEmpty()) ? null : toiletName;
    }
} 