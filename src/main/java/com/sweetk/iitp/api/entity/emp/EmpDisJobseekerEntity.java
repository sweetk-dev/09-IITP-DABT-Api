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