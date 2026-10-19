package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

//10. 장애인기업종합지원센터 창업넷 일반강좌 정보
@Entity
@Table(name = "emp_dis_startup_lecture")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDisStartupLectureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "online_type", length = 20, nullable = false)
    private String onlineType;

    @Column(name = "category", length = 50, nullable = false)
    private String category;

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "hours", nullable = false)
    private Integer hours;

    @Column(name = "recruit_count", nullable = false)
    private Integer recruitCount;

    @Column(name = "apply_start_date", nullable = false)
    private LocalDate applyStartDate;

    @Column(name = "apply_end_date", nullable = false)
    private LocalDate applyEndDate;

    @Column(name = "org_name", length = 100, nullable = false)
    private String orgName;

    @Column(name = "apply_count", nullable = false)
    private Integer applyCount;

    @Column(name = "complete_count", nullable = false)
    private Integer completeCount;

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