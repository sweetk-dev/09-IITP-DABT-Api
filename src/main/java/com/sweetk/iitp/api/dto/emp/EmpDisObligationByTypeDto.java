package com.sweetk.iitp.api.dto.emp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisObligationByTypeDto {
    private String orgType;
    private String category;
    private Integer total;
    private Integer limbSevere;
    private Integer limbMild;
    private Integer brainSevere;
    private Integer brainMild;
    private Integer visionSevere;
    private Integer visionMild;
    private Integer hearingSevere;
    private Integer hearingMild;
    private Integer speechSevere;
    private Integer speechMild;
    private Integer intellectual;
    private Integer mentalSevere;
    private Integer mentalMild;
    private Integer autism;
    private Integer kidneySevere;
    private Integer kidneyMild;
    private Integer heartSevere;
    private Integer heartMild;
    private Integer lungSevere;
    private Integer lungMild;
    private Integer liverSevere;
    private Integer liverMild;
    private Integer faceSevere;
    private Integer faceMild;
    private Integer stomaSevere;
    private Integer stomaMild;
    private Integer epilepsySevere;
    private Integer epilepsyMild;
    private Integer veteran;
} 