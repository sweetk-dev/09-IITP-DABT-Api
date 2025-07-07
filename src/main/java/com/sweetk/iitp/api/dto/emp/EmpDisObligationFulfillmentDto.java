package com.sweetk.iitp.api.dto.emp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisObligationFulfillmentDto {
    private Integer year;
    private Integer companyCount;
    private Integer fulfilledCount;
} 