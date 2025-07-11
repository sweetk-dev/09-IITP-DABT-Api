package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

//11. 장애인 표준사업장 현황
@Entity
@Table(name = "emp_dis_std_workplace")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDisStdWorkplaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cert_no", length = 30, nullable = false)
    private String certNo;

    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;

    @Column(name = "branch", length = 50, nullable = false)
    private String branch;

    @Column(name = "ceo", length = 50, nullable = false)
    private String ceo;

    @Column(name = "business_no", length = 20, nullable = false)
    private String businessNo;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "cert_date", nullable = false)
    private LocalDate certDate;

    @Column(name = "tel", length = 20, nullable = false)
    private String tel;

    @Column(name = "business_item", length = 200, nullable = false)
    private String businessItem;

    @Column(name = "type", length = 20, nullable = false)
    private String type;

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