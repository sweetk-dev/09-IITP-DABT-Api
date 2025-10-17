package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "장애인고용 부담금감면 연계고용사업장 정보")
public class EmpDisBurdenWorkplaceDto {
    @Schema(description = "연도", example = "2024")
    private Integer year;
    @Schema(description = "사업장명", example = "춘강장애인근로센터")
    private String companyName;
    @Schema(description = "시설구분", example = "직업재활시설")
    private String facilityType;
    @Schema(description = "사업장소재지", example = "제주특별자치도 제주시")
    private String address;
    @Schema(description = "도급품목", example = "세탁용역")
    private String workItem;
    @Schema(description = "상시근로자수", example = "92")
    private Integer workerCount;
    @Schema(description = "장애인수", example = "57")
    private Integer disabledCount;
    @Schema(description = "중증장애인수", example = "40")
    private Integer severeCount;
} 