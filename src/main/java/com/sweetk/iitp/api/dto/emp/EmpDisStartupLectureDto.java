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
@Schema(description = "장애인기업종합지원센터 창업넷 일반강좌 정보")
public class EmpDisStartupLectureDto {
    @Schema(description = "교육년도", example = "2018")
    private Integer year;
    @Schema(description = "온오프라인", example = "오프라인")
    private String onlineType;
    @Schema(description = "교육구분", example = "특화전문교육")
    private String category;
    @Schema(description = "교육제목", example = "기초교육")
    private String title;
    @Schema(description = "교육시작일", example = "2018-12-10")
    private LocalDate startDate;
    @Schema(description = "교육종료일", example = "2018-12-11")
    private LocalDate endDate;
    @Schema(description = "교육시간", example = "12")
    private Integer hours;
    @Schema(description = "모집인원", example = "21")
    private Integer recruitCount;
    @Schema(description = "신청시작일", example = "2018-12-10")
    private LocalDate applyStartDate;
    @Schema(description = "신청종료일", example = "2018-12-11")
    private LocalDate applyEndDate;
    @Schema(description = "교육기관", example = "장애인기업종합지원센터")
    private String orgName;
    @Schema(description = "신청인원", example = "21")
    private Integer applyCount;
    @Schema(description = "수료인원", example = "21")
    private Integer completeCount;
} 