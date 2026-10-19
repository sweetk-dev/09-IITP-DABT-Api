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
@Schema(description = "철도 역 검색 조건 (모두 선택)")
public class TransitRailSearchReq {

    @Schema(description = "역명 (부분 일치)", example = "안양")
    @Size(min = 1, max = 60)
    private String stnName;

    @Schema(description = "노선명 (정확히 일치)", example = "1호선")
    @Size(min = 1, max = 60)
    private String lineName;

    @Schema(description = "장애인 화장실만 (화장실 조회에만 적용)", allowableValues = {"Y", "N"})
    @Pattern(regexp = "^[YN]$", message = "Y 또는 N")
    private String disabledYn;
}
