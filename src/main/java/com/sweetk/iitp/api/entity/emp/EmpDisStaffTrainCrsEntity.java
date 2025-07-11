package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

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