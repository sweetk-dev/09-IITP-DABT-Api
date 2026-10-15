package com.sweetk.iitp.api.dto.basic.local;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Schema(description = "읍면동 등록장애인 검색 조건 (모두 선택)")
public class GgDisRegEmdSearchReq {

    @Schema(description = "시군명 (정확히 일치)", example = "안양시")
    @Size(min = 2, message = "시군명은 최소 2글자 이상이어야 합니다")
    private String sigunName;

    @Schema(description = "일반구명 (정확히 일치)", example = "만안구")
    @Size(min = 2, message = "구명은 최소 2글자 이상이어야 합니다")
    private String guName;

    @Schema(description = "읍면동명 (부분 일치, 최소 2글자)", example = "안양1")
    @Size(min = 2, message = "읍면동명은 최소 2글자 이상이어야 합니다")
    private String emdName;

    @Schema(description = "장애유형 (정확히 일치) — 지체, 뇌병변, 시각, 청각, 언어, 지적, 자폐성, 정신, 신장, 심장, 호흡기, 간, 안면, 장루·요루, 뇌전증",
            example = "지체")
    private String disType;

    @Schema(description = "원천 기준일(판). 비우면 시군마다 최신 판", example = "2025-08-07")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate baseDt;
}
