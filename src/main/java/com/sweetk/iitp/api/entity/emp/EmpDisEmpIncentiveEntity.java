package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.time.LocalDate;

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

    /**
     * 원천 판 기준일 (src_base_dt)
     * - 원천 파일이 기준으로 삼는 날짜(예: 2025-12-31 기준 파일). 수집기가 적재할 때 판(edition)마다 기록한다.
     * - 이 표는 연도 컬럼이 없어서 과거 판과 최신 판이 한 테이블에 함께 쌓인다.
     *   그래서 조회할 때는 이 값으로 판을 하나 골라서 내려준다 (기본: 가장 최근 판, EmpEditionFilter 참고).
     * - 값이 아직 채워지지 않은 행은 NULL 이다. NULL 만 있는 동안에는 NULL 판 하나로 취급한다.
     */
    @Column(name = "src_base_dt")
    private LocalDate srcBaseDt;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 