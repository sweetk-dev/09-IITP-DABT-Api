package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 지역별 장애인 고용 현황 엔티티
 */
@Entity
@Table(name = "emp_dis_regional_status")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDisRegionalStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "seq_no", nullable = false)
    private Integer seqNo;

    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @Column(name = "company_count", nullable = false)
    private Integer companyCount;

    @Column(name = "worker_count", nullable = false)
    private Integer workerCount;

    @Column(name = "obligation_count", nullable = false)
    private Integer obligationCount;

    @Column(name = "severe_count", nullable = false)
    private Integer severeCount;

    @Column(name = "severe_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal severeRate;

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