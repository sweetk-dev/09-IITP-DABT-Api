package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

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
} 