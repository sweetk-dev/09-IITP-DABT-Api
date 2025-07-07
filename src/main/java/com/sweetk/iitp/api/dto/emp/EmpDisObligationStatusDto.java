package com.sweetk.iitp.api.dto.emp;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisObligationStatusDto {
    private Integer seqNo;
    private String orgName;
    private Integer workplaceCount;
    private Integer workerCount;
    private Integer disabledCount;
    private BigDecimal empRate;
} 