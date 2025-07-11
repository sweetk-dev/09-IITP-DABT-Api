package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "장애인 의무고용 사업체 장애유형별 고용현황")
public class EmpDisObligationByTypeDto {
    @Schema(description = "사업체유형", example = "국가 및 지방자치단체")
    private String orgType;
    @Schema(description = "구분", example = "중앙행정기관")
    private String category;
    @Schema(description = "계", example = "1941")
    private Integer total;
    @Schema(description = "지체 중증", example = "126")
    private Integer limbSevere;
    @Schema(description = "지체 경증", example = "950")
    private Integer limbMild;
    @Schema(description = "뇌병변 중증", example = "30")
    private Integer brainSevere;
    @Schema(description = "뇌병변 경증", example = "70")
    private Integer brainMild;
    @Schema(description = "시각 중증", example = "16")
    private Integer visionSevere;
    @Schema(description = "시각 경증", example = "235")
    private Integer visionMild;
    @Schema(description = "청각 중증", example = "36")
    private Integer hearingSevere;
    @Schema(description = "청각 경증", example = "146")
    private Integer hearingMild;
    @Schema(description = "언어 중증", example = "0")
    private Integer speechSevere;
    @Schema(description = "언어 경증", example = "21")
    private Integer speechMild;
    @Schema(description = "지적", example = "79")
    private Integer intellectual;
    @Schema(description = "정신 중증", example = "27")
    private Integer mentalSevere;
    @Schema(description = "정신 경증", example = "0")
    private Integer mentalMild;
    @Schema(description = "자폐성", example = "10")
    private Integer autism;
    @Schema(description = "신장 중증", example = "20")
    private Integer kidneySevere;
    @Schema(description = "신장 경증", example = "45")
    private Integer kidneyMild;
    @Schema(description = "심장 중증", example = "6")
    private Integer heartSevere;
    @Schema(description = "심장 경증", example = "3")
    private Integer heartMild;
    @Schema(description = "호흡기 중증", example = "2")
    private Integer lungSevere;
    @Schema(description = "호흡기 경증", example = "0")
    private Integer lungMild;
    @Schema(description = "간 중증", example = "0")
    private Integer liverSevere;
    @Schema(description = "간 경증", example = "21")
    private Integer liverMild;
    @Schema(description = "안면 중증", example = "1")
    private Integer faceSevere;
    @Schema(description = "안면 경증", example = "6")
    private Integer faceMild;
    @Schema(description = "장루요루 중증", example = "0")
    private Integer stomaSevere;
    @Schema(description = "장루요루 경증", example = "3")
    private Integer stomaMild;
    @Schema(description = "뇌전증 중증", example = "4")
    private Integer epilepsySevere;
    @Schema(description = "뇌전증 경증", example = "6")
    private Integer epilepsyMild;
    @Schema(description = "국가유공", example = "78")
    private Integer veteran;
} 