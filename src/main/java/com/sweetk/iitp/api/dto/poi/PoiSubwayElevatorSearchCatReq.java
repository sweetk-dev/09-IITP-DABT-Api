package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.PoiSubwayNodeType;
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
    
    @Schema(description = "시도 코드 (7자리)", example = "9110000")
    @Pattern(regexp = "^\\d{7}$", message = "시도 코드는 7자리 숫자여야 합니다")
    private String sidoCode;
    
    @Schema(description = "노드 유형 코드 (0: 일반노드, 1: 지하철 출입구, 2: 버스 정류장, 3: 지하보도 출입구)", 
            example = "1", 
            allowableValues = {"0", "1", "2", "3"})
    private PoiSubwayNodeType nodeTypeCode;
    
    public void setStationName(String stationName) {
        this.stationName = (stationName != null && stationName.trim().isEmpty()) ? null : stationName;
    }
    
    /**
     * 노드 유형 코드의 한글 명칭 반환
     */
    public String getNodeTypeName() {
        return nodeTypeCode != null ? nodeTypeCode.getName() : null;
    }
    
    /**
     * 노드 유형 코드의 숫자 값 반환
     */
    public Integer getNodeTypeCodeValue() {
        return nodeTypeCode != null ? nodeTypeCode.getCode() : null;
    }
} 