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
public class EmpDisStaffTrainCrsDto {
    private String courseType;
    private String courseName;
    private String courseContent;
    private String method;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer recruitCount;
    private LocalDate applyStartDate;
    private LocalDate applyEndDate;
    private String applyMethod;
    private String location;
    private String target;
} 