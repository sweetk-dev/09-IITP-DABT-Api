package com.sweetk.iitp.api.entity.emp;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;



//06. 장애인고용 부담금감면 연계고용사업장 정보
@Entity
@Table(name = "emp_dis_burden_workplace")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpDisBurdenWorkplaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer year;

    @Column(length = 200, nullable = false)
    private String companyName;

    @Column(length = 50, nullable = false)
    private String facilityType;

    @Column(length = 500, nullable = false)
    private String address;

    @Column(length = 200, nullable = false)
    private String workItem;

    @Column(nullable = false)
    private Integer workerCount;

    @Column(nullable = false)
    private Integer disabledCount;

    @Column(nullable = false)
    private Integer severeCount;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
} 