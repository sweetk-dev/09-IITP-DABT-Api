package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

//08. 고용개발원 교육정보
@Entity
@Table(name = "emp_dis_staff_train_crs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDisStaffTrainCrsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_type", length = 50, nullable = false)
    private String courseType;

    @Column(name = "course_name", length = 100, nullable = false)
    private String courseName;

    @Column(name = "course_content", columnDefinition = "text", nullable = false)
    private String courseContent;

    @Column(name = "method", length = 50, nullable = false)
    private String method;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "recruit_count", nullable = false)
    private Integer recruitCount;

    @Column(name = "apply_start_date")
    private LocalDate applyStartDate;

    @Column(name = "apply_end_date")
    private LocalDate applyEndDate;

    @Column(name = "apply_method", length = 200)
    private String applyMethod;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "target", length = 200)
    private String target;

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