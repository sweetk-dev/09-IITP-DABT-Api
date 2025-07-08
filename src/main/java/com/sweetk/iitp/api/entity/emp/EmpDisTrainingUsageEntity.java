package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "emp_dis_training_usage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisTrainingUsageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String orgCategory;

    @Column(nullable = false)
    private Integer userCount;

    @Column(nullable = false)
    private Integer employedCount;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 