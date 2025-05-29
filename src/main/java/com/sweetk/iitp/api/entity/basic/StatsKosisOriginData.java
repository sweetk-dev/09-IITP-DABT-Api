package com.sweetk.iitp.api.entity.basic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


/**
 * 수집해 온 KOSIS 원천 통계 데이터
 */
@Entity
@Table(name = "stats_kosis_origin_data")
@Getter
@Setter
@NoArgsConstructor
public class StatsKosisOriginData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "src_data_id", nullable = false)
    private StatsSrcDataInfo srcDataInfo;

    @Column(name = "org_id", nullable = false)
    private Short orgId;

    @Column(name = "tbl_id", length = 40, nullable = false)
    private String tblId;

    @Column(name = "tbl_nm", length = 300, nullable = false)
    private String tblNm;

    @Column(name = "c1", length = 24, nullable = false)
    private String c1;

    @Column(name = "c2", length = 24)
    private String c2;

    @Column(name = "c3", length = 24)
    private String c3;

    @Column(name = "c4", length = 24)
    private String c4;

    @Column(name = "c1_obj_nm", length = 300, nullable = false)
    private String c1ObjNm;

    @Column(name = "c2_obj_nm", length = 300)
    private String c2ObjNm;

    @Column(name = "c3_obj_nm", length = 300)
    private String c3ObjNm;

    @Column(name = "c4_obj_nm", length = 300)
    private String c4ObjNm;

    @Column(name = "c1_nm", length = 300, nullable = false)
    private String c1Nm;

    @Column(name = "c2_nm", length = 300)
    private String c2Nm;

    @Column(name = "c3_nm", length = 300)
    private String c3Nm;

    @Column(name = "c4_nm", length = 300)
    private String c4Nm;

    @Column(name = "itm_id", length = 24, nullable = false)
    private String itmId;

    @Column(name = "itm_nm", length = 300, nullable = false)
    private String itmNm;

    @Column(name = "unit_nm", length = 20)
    private String unitNm;

    @Column(name = "prd_se", length = 2, nullable = false)
    private String prdSe;

    @Column(name = "prd_de", length = 10, nullable = false)
    private String prdDe;

    @Column(name = "dt", length = 100, nullable = false)
    private String dt;

    @Column(name = "lst_chn_de", length = 10)
    private String lstChnDe;

    @Column(name = "stat_latest_chn_dt", length = 10)
    private String statLatestChnDt;

    @Column(name = "data_ref_dt")
    private LocalDate dataRefDt;

    @Column(name = "created_at", nullable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "created_by", length = 40, nullable = false)
    private String createdBy;
}