package com.sweetk.iitp.api.dto.transit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "버스 노선 검색 조건 (모두 선택)")
public class TransitBusRouteSearchReq {

    @Schema(description = "노선 번호 (부분 일치)", example = "5-1")
    @Size(min = 1, max = 30)
    private String routeName;

    @Schema(description = "운행 지역 (부분 일치, 최소 2글자)", example = "안양")
    @Size(min = 2, message = "운행 지역은 최소 2글자 이상이어야 합니다")
    private String regionName;

    @Schema(description = "저상버스 운행 여부", allowableValues = {"Y", "N"})
    @Pattern(regexp = "^[YN]$", message = "Y 또는 N")
    private String lowBusYn;
}
