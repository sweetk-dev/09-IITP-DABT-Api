package com.sweetk.iitp.api.entity.basic;

import com.sweetk.iitp.api.constant.SysConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * 통계 테이블 기본 entity
 */
@MappedSuperclass
@Getter
@Setter
@SQLRestriction("deleted_at IS NULL")
public abstract class BaseStatsDtStringEntity implements StatsCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "src_data_id", nullable = false)
    private Integer srcDataId;

    @Column(name = "prd_de", nullable = false)
    private Short prdDe;

    @Column(name = "c1", length = 24, nullable = false)
    private String c1;

    @Column(name = "c2", length = 24)
    private String c2;

    @Column(name = "c3", length = 24)
    private String c3;

//    @Column(name = "c1_obj_nm", length = 300)
//    private String c1ObjNm;
//
//    @Column(name = "c2_obj_nm", length = 300)
//    private String c2ObjNm;
//
//    @Column(name = "c3_obj_nm", length = 300)
//    private String c3ObjNm;

    @Column(name = "itm_id", length = 24, nullable = false)
    private String itmId;

    @Column(name = "unit_nm", length = 20)
    private String unitNm;

    @Column(name = "dt", nullable = false)
    private String dt;

    @Column(name = "lst_chn_de")
    private LocalDate lstChnDe;

    @Column(name = "src_latest_chn_dt")
    private LocalDate srcLatestChnDt;

    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn = SysConstants.YN_N;


    @CreatedDate
    @Column(updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    private OffsetDateTime updatedAt;

    @Column(name = "created_by", length = 40, nullable = false)
    private String createdBy;

    @Column(name = "updated_by", length = 40)
    private String updatedBy;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "deleted_by", length = 40)
    private String deletedBy;

    //Getter
    @Override public Integer getId() { return id; }
    @Override public Integer getSrcDataId() { return srcDataId; }
    @Override public Short getPrdDe() { return prdDe; }
    @Override public String getC1() { return c1; }
    @Override public String getC2() { return c2; }
    @Override public String getC3() { return c3; }
//    @Override public String getC1ObjNm() { return c1ObjNm; };
//    @Override public String getC2ObjNm() { return c2ObjNm; };
//    @Override public String getC3ObjNm() { return c3ObjNm; };
    @Override public String getItmId() { return itmId; }
    @Override public String getUnitNm() { return unitNm; }
    @Override public String getDt() { return dt; }
    @Override public LocalDate getLstChnDe() { return lstChnDe; }
    @Override public LocalDate getSrcLatestChnDt() { return srcLatestChnDt; }
    @Override public OffsetDateTime getCreatedAt() { return createdAt; }
    @Override public OffsetDateTime getUpdatedAt() { return updatedAt; }
    @Override public String getCreatedBy() { return createdBy; }
    @Override public String getUpdatedBy() { return updatedBy; }

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        delYn = SysConstants.YN_N;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public void softDelete(String deletedBy) {
        this.deletedAt = OffsetDateTime.now();
        this.deletedBy = deletedBy;
        this.delYn = SysConstants.YN_Y;
    }
}