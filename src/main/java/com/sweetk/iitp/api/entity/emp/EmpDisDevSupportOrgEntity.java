package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.time.LocalDate;

//09. 한국장애인개발원 발달장애인 지원 기관 및 제공서비스
@Entity
@Table(name = "emp_dis_dev_support_org")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDisDevSupportOrgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "seq_no", nullable = false)
    private Integer seqNo;

    @Column(name = "org_name", length = 200, nullable = false)
    private String orgName;

    @Column(name = "region", length = 100, nullable = false)
    private String region;

    @Column(name = "day_activity", nullable = false)
    private Boolean dayActivity;

    @Column(name = "afterschool", nullable = false)
    private Boolean afterschool;

    @Column(name = "indiv_plan", nullable = false)
    private Boolean indivPlan;

    @Column(name = "parent_edu", nullable = false)
    private Boolean parentEdu;

    @Column(name = "family_rest", nullable = false)
    private Boolean familyRest;

    @Column(name = "parent_counsel", nullable = false)
    private Boolean parentCounsel;

    @Column(name = "rights_relief", nullable = false)
    private Boolean rightsRelief;

    @Column(name = "public_guardian", nullable = false)
    private Boolean publicGuardian;

    @Column(name = "child_family_sup", nullable = false)
    private Boolean childFamilySup;

    @Column(name = "emergency_care", nullable = false)
    private Boolean emergencyCare;

    /**
     * 최중증 통합돌봄 제공 여부 (severe_integrated_care)
     * - 원천 파일에 새로 생긴 서비스 항목. 이 항목이 없던 과거 판에서 온 행은 NULL 이다.
     */
    @Column(name = "severe_integrated_care")
    private Boolean severeIntegratedCare;

    /**
     * 원천 판 기준일 (src_base_dt)
     * - 원천 파일이 기준으로 삼는 날짜(예: 2025-12-31 기준 파일). 수집기가 적재할 때 판(edition)마다 기록한다.
     * - 이 표는 연도 컬럼이 없어서 과거 판과 최신 판이 한 테이블에 함께 쌓인다.
     *   그래서 조회할 때는 이 값으로 판을 하나 골라서 내려준다 (기본: 가장 최근 판, EmpEditionFilter 참고).
     * - 값이 아직 채워지지 않은 행은 NULL 이다. NULL 만 있는 동안에는 NULL 판 하나로 취급한다.
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