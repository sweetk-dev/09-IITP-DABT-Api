package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisJobseekerStatusDto {
    private Integer id;
    private Integer seqNo;
    private LocalDate regDate;
    private Short age;
    private String region;
    private String jobType;
    private String salaryType;
    private Integer salaryAmount;
    private String disabilityType;
    private String severity;
    private String orgType;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 