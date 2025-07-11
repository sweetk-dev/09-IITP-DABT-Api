package com.sweetk.iitp.api.dto.emp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDisStartupLectureDto {
    private Integer year;
    private String onlineType;
    private String category;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer hours;
    private Integer recruitCount;
    private LocalDate applyStartDate;
    private LocalDate applyEndDate;
    private String orgName;
    private Integer applyCount;
    private Integer completeCount;
} 