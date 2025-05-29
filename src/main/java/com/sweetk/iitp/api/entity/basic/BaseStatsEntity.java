package com.sweetk.iitp.api.entity.basic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 통계 테이블 기본 entity
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "src_data_id", nullable = false)
    private StatsSrcDataInfo srcDataInfo;

    @Column(name = "prd_de", nullable = false)
    private Short prdDe;

    @Column(name = "c1", length = 24, nullable = false)
    private String c1;

    @Column(name = "c2", length = 24)
    private String c2;

    @Column(name = "c3", length = 24)
    private String c3;

    @Column(name = "itm_id", length = 24, nullable = false)
    private String itmId;

    @Column(name = "unit_nm", length = 20)
    private String unitNm;

    @Column(name = "dt", precision = 15, scale = 3, nullable = false)
    private BigDecimal dt;

    @Column(name = "lst_chn_de")
    private LocalDate lstChnDe;

    @Column(name = "src_latest_chn_dt")
    private LocalDate srcLatestChnDt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "created_by", length = 40, nullable = false)
    private String createdBy;

    @Column(name = "updated_by", length = 40)
    private String updatedBy;
}