package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

//13. 신규고용장려금 지역별 지급 현황
@Entity
@Table(name = "emp_dis_emp_incentive")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisEmpIncentiveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String region;

    @Column(length = 100, nullable = false)
    private String industry;

    @Column(nullable = false)
    private Integer companyCount;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Integer paidPerson;

    @Column(nullable = false)
    private Integer paidYearPerson;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 