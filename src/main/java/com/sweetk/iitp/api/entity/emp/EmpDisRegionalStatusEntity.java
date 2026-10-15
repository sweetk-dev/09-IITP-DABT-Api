package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;


//07. 지역별 장애인 고용 현황
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

    /**
     * 원천 판 기준일 (src_base_dt)
     * - 원천 파일이 기준으로 삼는 날짜. 수집기가 적재할 때 판(edition)마다 기록한다.
     * - 이 표는 연도(year) 컬럼이 있고 연도마다 한 판만 두므로, 조회 조건에는 쓰지 않고 참고 정보로만 내려준다.
     * - 값이 아직 채워지지 않은 행은 NULL 이다.
     */
    @Column(name = "src_base_dt")
    private LocalDate srcBaseDt;

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