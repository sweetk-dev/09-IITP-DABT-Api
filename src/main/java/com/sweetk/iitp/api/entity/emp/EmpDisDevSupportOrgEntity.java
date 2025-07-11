package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

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