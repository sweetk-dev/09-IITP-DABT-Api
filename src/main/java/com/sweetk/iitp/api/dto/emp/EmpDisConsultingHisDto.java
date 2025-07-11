package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisConsultingHisDto {
    private Integer id;
    private Integer seqNo;
    private String diagnosisNo;
    private LocalDate receiveDate;
    private String businessNo;
    private String companyName;
    private String address;
    private String businessType;
    private String regionalOffice;
    private String officeTel;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 