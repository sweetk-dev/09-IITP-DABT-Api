package com.sweetk.iitp.api.dto.poi;

import com.sweetk.iitp.api.constant.poi.PoiEmergencySupportType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "긴급대응 지원 거점 검색 조건 (모두 선택)")
public class PoiEmergencySupportSearchReq {

    @Schema(description = "거점 종류 [charge:전동보장구 급속충전기, repair:보조기기 수리·지원 센터, calltaxi:교통약자 이동지원센터]",
            example = "charge", allowableValues = {"charge", "repair", "calltaxi"})
    private PoiEmergencySupportType supportType;

    @Schema(description = "시도 코드 (7자리)", example = "9410000")
    @Pattern(regexp = "^\\d{7}$", message = "시도 코드는 7자리 숫자여야 합니다")
    private String sidoCode;

    @Schema(description = "시군구명 (부분 일치, 최소 2글자)", example = "안양")
    @Size(min = 2, message = "시군구명은 최소 2글자 이상이어야 합니다")
    private String sggName;

    @Schema(description = "거점명 (부분 일치, 최소 2글자)", example = "주민센터")
    @Size(min = 2, message = "거점명은 최소 2글자 이상이어야 합니다")
    private String name;
}
