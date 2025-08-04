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
@Schema(description = "지하철 엘리베이터 카테고리 검색 요청")
public class PoiSubwayElevatorSearchCatReq {
    
    @Schema(description = "지하철역명 검색 (최소 2글자)", example = "혜화")
    @Size(min = 2, message = "지하철역명은 최소 2글자 이상이어야 합니다")
    private String stationName;
    
    @Schema(description = "시도 코드 (7자리)", example = "1100000")
    @Pattern(regexp = "^\\d{7}$", message = "시도 코드는 7자리 숫자여야 합니다")
    private String sidoCode;
    
    @Schema(description = "노드 유형 코드", example = "1", 
            allowableValues = {"0", "1", "2", "3"})
    private Integer nodeTypeCode;
    
    public void setStationName(String stationName) {
        this.stationName = (stationName != null && stationName.trim().isEmpty()) ? null : stationName;
    }
} 