package com.sweetk.iitp.api.dto.emp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "장애인 구직자 현황 DTO")
public class EmpDisJobseekerDto {
    @Schema(description = "순번", example = "1")
    private Integer seqNo;
    @Schema(description = "등록일", example = "2024-01-01")
    private LocalDate regDate;
    @Schema(description = "나이", example = "30")
    private Short age;
    @Schema(description = "지역", example = "서울")
    private String region;
    @Schema(description = "직종", example = "사무직")
    private String jobType;
    @Schema(description = "임금형태", example = "월급")
    private String salaryType;
    @Schema(description = "임금액", example = "2500000")
    private Integer salaryAmount;
    @Schema(description = "장애유형", example = "지체장애")
    private String disabilityType;
    @Schema(description = "중증여부", example = "중증")
    private String severity;
    @Schema(description = "기관유형", example = "공공기관")
    private String orgType;
} 