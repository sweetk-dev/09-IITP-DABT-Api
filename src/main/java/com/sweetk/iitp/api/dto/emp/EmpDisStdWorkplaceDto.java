package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisStdWorkplaceDto {
    private Integer id;
    private String certNo;
    private String companyName;
    private String branch;
    private String ceo;
    private String businessNo;
    private String address;
    private LocalDate certDate;
    private String tel;
    private String businessItem;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 