package com.sweetk.iitp.api.dto.emp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "발달장애인 지원 기관 및 제공 서비스")
public class EmpDisDevSupportOrgDto {
    @Schema(description = "연번", example = "1")
    private Integer seqNo;
    @Schema(description = "기관명", example = "해피데이 충주시 청소년 발달장애학생 방과후 활동센터")
    private String orgName;
    @Schema(description = "시군구", example = "충청북도 충주시")
    private String region;
    @Schema(description = "주간활동", example = "false")
    private Boolean dayActivity;
    @Schema(description = "청소년방과후활동", example = "true")
    private Boolean afterschool;
    @Schema(description = "개인별지원계획", example = "false")
    private Boolean indivPlan;
    @Schema(description = "부모교육", example = "false")
    private Boolean parentEdu;
    @Schema(description = "가족휴식", example = "false")
    private Boolean familyRest;
    @Schema(description = "부모상담", example = "false")
    private Boolean parentCounsel;
    @Schema(description = "권리구제", example = "false")
    private Boolean rightsRelief;
    @Schema(description = "공공후견", example = "false")
    private Boolean publicGuardian;
    @Schema(description = "장애아가족양육지원", example = "false")
    private Boolean childFamilySup;
    @Schema(description = "긴급돌봄", example = "false")
    private Boolean emergencyCare;
} 