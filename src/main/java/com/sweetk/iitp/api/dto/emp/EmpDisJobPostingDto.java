package com.sweetk.iitp.api.dto.emp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "장애인 구인 정보 DTO")
public class EmpDisJobPostingDto {
    @Schema(description = "순번", example = "1")
    private Integer seqNo;
    @Schema(description = "구인신청일자", example = "2024-01-01")
    private LocalDate applyDate;
    @Schema(description = "구인기간", example = "2024-01-01 ~ 2024-01-31")
    private String recruitPeriod;
    @Schema(description = "사업장명", example = "행복회사")
    private String companyName;
    @Schema(description = "직종", example = "사무직")
    private String jobType;
    @Schema(description = "고용형태 ", example = "정규직")
    private String empType;
    @Schema(description = "임금형태", example = "월급")
    private String salaryType;
    @Schema(description = "임금액", example = "2500000")
    private Integer salaryAmount;
    @Schema(description = "채용형태", example = "공개채용")
    private String hireType;
    @Schema(description = "경력", example = "신입")
    private String experience;
    @Schema(description = "학력", example = "대졸")
    private String education;
    @Schema(description = "전공", example = "경영학")
    private String major;
    @Schema(description = "자격증", example = "컴퓨터활용능력")
    private String license;
    @Schema(description = "주소", example = "서울특별시 강남구 ...")
    private String address;
    @Schema(description = "기업형태", example = "중소기업")
    private String companyType;
    @Schema(description = "담당기관", example = "서울고용센터")
    private String office;
    @Schema(description = "등록일", example = "2024-01-01")
    private LocalDate regDate;
    @Schema(description = "전화번호", example = "02-1234-5678")
    private String tel;
} 