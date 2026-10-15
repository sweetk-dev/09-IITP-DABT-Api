package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.LocalDate;

//14. 산업별 의무고용 현황
@Entity
@Table(name = "emp_dis_obligation_by_indust")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisObligationByIndustEntity {
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
    private BigDecimal empRate;

    /**
     * 원천 판 기준일 (src_base_dt)
     * - 원천 파일이 기준으로 삼는 날짜. 수집기가 적재할 때 판(edition)마다 기록한다.
     * - 이 표는 연도(year) 컬럼이 있고 연도마다 한 판만 두므로, 조회 조건에는 쓰지 않고 참고 정보로만 내려준다.
     * - 값이 아직 채워지지 않은 행은 NULL 이다.
     */
    @Column(name = "src_base_dt")
    private LocalDate srcBaseDt;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private String createdBy;
    private String updatedBy;
} 