package com.sweetk.iitp.api.dto.transit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "버스 정류소 검색 조건 (모두 선택)")
public class TransitBusStationSearchReq {

    @Schema(description = "정류소명 (부분 일치, 최소 2글자)", example = "안양역")
    @Size(min = 2, message = "정류소명은 최소 2글자 이상이어야 합니다")
    private String stationName;

    @Schema(description = "정류소 번호(모바일 번호, 정확히 일치)", example = "25001")
    @Size(min = 1, max = 20)
    private String mobileNo;

    @Schema(description = "지역명 (부분 일치, 최소 2글자)", example = "안양")
    @Size(min = 2, message = "지역명은 최소 2글자 이상이어야 합니다")
    private String regionName;
}
