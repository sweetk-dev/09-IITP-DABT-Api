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