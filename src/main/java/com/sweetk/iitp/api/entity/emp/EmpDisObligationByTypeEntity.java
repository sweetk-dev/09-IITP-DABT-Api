package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

//12. 장애인 의무고용 사업체 장애유형별 고용현황
@Entity
@Table(name = "emp_dis_obligation_by_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisObligationByTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String orgType;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false)
    private Integer total;
    @Column(nullable = false)
    private Integer limbSevere;
    @Column(nullable = false)
    private Integer limbMild;
    @Column(nullable = false)
    private Integer brainSevere;
    @Column(nullable = false)
    private Integer brainMild;
    @Column(nullable = false)
    private Integer visionSevere;
    @Column(nullable = false)
    private Integer visionMild;
    @Column(nullable = false)
    private Integer hearingSevere;
    @Column(nullable = false)
    private Integer hearingMild;
    @Column(nullable = false)
    private Integer speechSevere;
    @Column(nullable = false)
    private Integer speechMild;
    @Column(nullable = false)
    private Integer intellectual;
    @Column(nullable = false)
    private Integer mentalSevere;
    @Column(nullable = false)
    private Integer mentalMild;
    @Column(nullable = false)
    private Integer autism;
    @Column(nullable = false)
    private Integer kidneySevere;
    @Column(nullable = false)
    private Integer kidneyMild;
    @Column(nullable = false)
    private Integer heartSevere;
    @Column(nullable = false)
    private Integer heartMild;
    @Column(nullable = false)
    private Integer lungSevere;
    @Column(nullable = false)
    private Integer lungMild;
    @Column(nullable = false)
    private Integer liverSevere;
    @Column(nullable = false)
    private Integer liverMild;
    @Column(nullable = false)
    private Integer faceSevere;
    @Column(nullable = false)
    private Integer faceMild;
    @Column(nullable = false)
    private Integer stomaSevere;
    @Column(nullable = false)
    private Integer stomaMild;
    @Column(nullable = false)
    private Integer epilepsySevere;
    @Column(nullable = false)
    private Integer epilepsyMild;
    @Column(nullable = false)
    private Integer veteran;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 