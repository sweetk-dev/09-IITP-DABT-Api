package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;


//01. 장애인 구직자 현황
@Entity
@Table(name = "emp_dis_jobseeker_status")
@Getter
@Setter
@NoArgsConstructor
public class EmpDisJobseekerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "seq_no", nullable = false)
    private Integer seqNo;

    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate;

    @Column(name = "age", nullable = false)
    private Short age;

    @Column(name = "region", length = 100, nullable = false)
    private String region;

    @Column(name = "job_type", length = 100, nullable = false)
    private String jobType;

    @Column(name = "salary_type", length = 10, nullable = false)
    private String salaryType;

    @Column(name = "salary_amount", nullable = false)
    private Integer salaryAmount;

    @Column(name = "disability_type", length = 50, nullable = false)
    private String disabilityType;

    @Column(name = "severity", length = 10, nullable = false)
    private String severity;

    @Column(name = "org_type", length = 50, nullable = false)
    private String orgType;

    /**
     * 원천 판 기준일 (src_base_dt)
     * - 원천 파일이 기준으로 삼는 날짜(예: 2025-12-31 기준 파일). 수집기가 적재할 때 판(edition)마다 기록한다.
     * - 이 표는 연도 컬럼이 없어서 과거 판과 최신 판이 한 테이블에 함께 쌓인다.
     *   그래서 조회할 때는 이 값으로 판을 하나 골라서 내려준다 (기본: 가장 최근 판, EmpEditionFilter 참고).
     * - 값이 아직 채워지지 않은 행은 NULL 이다. NULL 만 있는 동안에는 NULL 판 하나로 취급한다.
     */
    @Column(name = "src_base_dt")
    private LocalDate srcBaseDt;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "created_by", length = 40)
    private String createdBy;

    @Column(name = "updated_by", length = 40)
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
} 