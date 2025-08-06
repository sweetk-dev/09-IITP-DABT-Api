package com.sweetk.iitp.api.dto.poi;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Schema(description = "위치 기반 검색 요청")
public class PoiBaseSearchLocReq {
    
    @Schema(description = "위도", example = "37.5665")
    @Min(value = -90, message = "위도는 -90에서 90 사이여야 합니다")
    @Max(value = 90, message = "위도는 -90에서 90 사이여야 합니다")
    private BigDecimal latitude;
    
    @Schema(description = "경도", example = "126.9780")
    @Min(value = -180, message = "경도는 -180에서 180 사이여야 합니다")
    @Max(value = 180, message = "경도는 -180에서 180 사이여야 합니다")
    private BigDecimal longitude;
    
    @Schema(description = "검색 반경(미터)", example = "1000")
    @Min(value = 0, message = "검색 반경은 0보다 커야 합니다")
    private BigDecimal radius;
} 