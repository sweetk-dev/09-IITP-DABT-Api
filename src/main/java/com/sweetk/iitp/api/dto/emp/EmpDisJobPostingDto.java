package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisJobPostingDto {
    private Integer id;
    private Integer seqNo;
    private LocalDate applyDate;
    private String recruitPeriod;
    private String companyName;
    private String jobType;
    private String empType;
    private String salaryType;
    private Integer salaryAmount;
    private String hireType;
    private String experience;
    private String education;
    private String major;
    private String license;
    private String address;
    private String companyType;
    private String office;
    private LocalDate regDate;
    private String tel;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 