package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * 고용-장애인 고용컨설팅
 */
@Entity
@Table(name = "emp_dis_consulting_his")
@Getter
@Setter
@NoArgsConstructor
public class EmpDisConsultingHisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "seq_no", nullable = false)
    private Integer seqNo;

    @Column(name = "diagnosis_no", length = 20, nullable = false)
    private String diagnosisNo;

    @Column(name = "receive_date", nullable = false)
    private LocalDate receiveDate;

    @Column(name = "business_no", length = 20, nullable = false)
    private String businessNo;

    @Column(name = "company_name", length = 200, nullable = false)
    private String companyName;

    @Column(name = "address", length = 500, nullable = false)
    private String address;

    @Column(name = "business_type", length = 100, nullable = false)
    private String businessType;

    @Column(name = "regional_office", length = 100, nullable = false)
    private String regionalOffice;

    @Column(name = "office_tel", length = 20, nullable = false)
    private String officeTel;

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