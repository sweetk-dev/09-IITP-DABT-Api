package com.sweetk.iitp.api.dto.emp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "장애인 고용 컨설팅 정보")
public class EmpDisConsultingHisDto {
    @Schema(description = "순번", example = "12")
    private Integer seqNo;
    @Schema(description = "진단번호", example = "E202411190003")
    private String diagnosisNo;
    @Schema(description = "접수일자", example = "2024-11-19")
    private LocalDate receiveDate;
    @Schema(description = "사업자등록번호", example = "647-87-00659")
    private String businessNo;
    @Schema(description = "사업체명", example = "휘닉스평창(주)")
    private String companyName;
    @Schema(description = "소재지주소", example = "강원도 평창군 봉평면 태기로 174")
    private String address;
    @Schema(description = "사업체유형", example = "민간기업")
    private String businessType;
    @Schema(description = "관할 지역본부 및 지사", example = "강원지사")
    private String regionalOffice;
    @Schema(description = "관할 지역본부 및 지사 대표번호", example = "033-737-6612")
    private String officeTel;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 