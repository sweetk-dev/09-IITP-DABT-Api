package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "장애인 의무고용 - 사업체 현황")
public class EmpDisObligationFulfillmentDto {
    @Schema(description = "연도", example = "2010")
    private Integer year;
    @Schema(description = "사업체수", example = "23249")
    private Integer companyCount;
    @Schema(description = "이행사업체수", example = "11898")
    private Integer fulfilledCount;
    @Schema(description = "원천 판 기준일 (YYYY-MM-DD). 원천 파일이 기준으로 삼는 날짜이며, 아직 기록되지 않은 데이터는 null", example = "2025-12-31")
    private LocalDate srcBaseDt;
} 