package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "emp_dis_regional_status")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDisRegionalStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 연도 (YYYY)
     */
    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "region", length = 100, nullable = false)
    private String region;

    @Column(name = "company_count", nullable = false)
    private Integer companyCount;

    @Column(name = "worker_count", nullable = false)
    private Integer workerCount;

    @Column(name = "obligation_count", nullable = false)
    private Integer obligationCount;

    @Column(name = "severe_2x_count", nullable = false)
    private Integer severe2xCount;

    @Column(name = "severe_2x_rate", precision = 5, scale = 2, nullable = false)
    private BigDecimal severe2xRate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", length = 40)
    private String createdBy;

    @Column(name = "updated_by", length = 40)
    private String updatedBy;
} 