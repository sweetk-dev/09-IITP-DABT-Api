package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * 고용-장애인 고용의무 현황 통계
 */
@Entity
@Table(name = "emp_dis_obligation_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisObligationStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer seqNo;

    @Column(length = 100, nullable = false)
    private String orgName;

    @Column(nullable = false)
    private Integer workplaceCount;

    @Column(nullable = false)
    private Integer workerCount;

    @Column(nullable = false)
    private Integer disabledCount;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal empRate;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private String createdBy;
    private String updatedBy;
} 