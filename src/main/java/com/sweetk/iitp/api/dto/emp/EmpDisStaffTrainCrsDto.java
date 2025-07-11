package com.sweetk.iitp.api.dto.emp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "고용개발원 교육정보(장애인고용 전문인력 교육과정)")
public class EmpDisStaffTrainCrsDto {
    @Schema(description = "과정구분", example = "장애인고용 전문인력 양성")
    private String courseType;
    @Schema(description = "과정명", example = "1차 직무지도원 양성과정")
    private String courseName;
    @Schema(description = "교육내용", example = "지원고용사업과 직무지도원의 역할, 장애인고용정책 등")
    private String courseContent;
    @Schema(description = "교육방법", example = "비대면 화상교육")
    private String method;
    @Schema(description = "교육시작일", example = "2025-03-27")
    private LocalDate startDate;
    @Schema(description = "교육종료일", example = "2025-03-28")
    private LocalDate endDate;
    @Schema(description = "모집인원", example = "50")
    private Integer recruitCount;
    @Schema(description = "수강신청 시작일", example = "2025-03-10")
    private LocalDate applyStartDate;
    @Schema(description = "수강신청 종료일", example = "2025-03-14")
    private LocalDate applyEndDate;
    @Schema(description = "수강신청방법", example = "EDI사이버연수원->교육신청->집합혼합교육->'2024년 직무지도원 양성과정' 클릭")
    private String applyMethod;
    @Schema(description = "교육장소", example = "자택")
    private String location;
    @Schema(description = "교육대상", example = "직무지도원 활동예정자")
    private String target;
} 