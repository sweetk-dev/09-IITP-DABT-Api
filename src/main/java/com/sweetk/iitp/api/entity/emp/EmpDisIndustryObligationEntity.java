package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "emp_dis_industry_obligation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisIndustryObligationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer year;

    @Column(length = 100, nullable = false)
    private String industry;

    @Column(nullable = false)
    private Integer companyCount;

    @Column(nullable = false)
    private Integer workerCount;

    @Column(nullable = false)
    private Integer obligationCount;

    @Column(nullable = false, precision = 5, scale = 2)
    private Double empRate;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private String createdBy;
    private String updatedBy;
} 