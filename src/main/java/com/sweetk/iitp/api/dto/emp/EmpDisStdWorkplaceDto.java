package com.sweetk.iitp.api.dto.emp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "장애인 표준사업장 현황")
public class EmpDisStdWorkplaceDto {
    @Schema(description = "인증번호", example = "제2013-001호")
    private String certNo;
    @Schema(description = "사업체명", example = "㈜포스코휴먼스")
    private String companyName;
    @Schema(description = "관할지사", example = "경북지사")
    private String branch;
    @Schema(description = "대표자", example = "이성록")
    private String ceo;
    @Schema(description = "사업자등록번호", example = "506-81-69850")
    private String businessNo;
    @Schema(description = "소재지", example = "경상북도 포항시 남구 동해안로 6213번길 15-1")
    private String address;
    @Schema(description = "인증일자", example = "2013-06-19")
    private LocalDate certDate;
    @Schema(description = "전화번호", example = "054-220-7733")
    private String tel;
    @Schema(description = "업종 및 주요생산품", example = "서비스업(사무지원), 세탁업, 차량관리사업")
    private String businessItem;
    @Schema(description = "구분", example = "자회사")
    private String type;
} 