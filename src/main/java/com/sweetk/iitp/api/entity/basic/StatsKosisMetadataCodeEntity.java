package com.sweetk.iitp.api.entity.basic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 수집해 온 KOSIS 원천 통계 데이터의 분류/항목 코드 정보
 */
@Entity
@Table(name = "stats_kosis_metadata_code")
@Getter
@Setter
@NoArgsConstructor
public class StatsKosisMetadataCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "src_data_id", nullable = false)
    private StatsSrcDataInfoEntity srcDataInfo;

    @Column(name = "tbl_id", length = 40, nullable = false)
    private String tblId;

    @Column(name = "obj_id", length = 40, nullable = false)
    private String objId;

    @Column(name = "obj_nm", length = 300, nullable = false)
    private String objNm;

    @Column(name = "itm_id", length = 40, nullable = false)
    private String itmId;

    @Column(name = "itm_nm", length = 300, nullable = false)
    private String itmNm;

    @Column(name = "up_itm_id", length = 40)
    private String upItmId;

    @Column(name = "obj_id_sn")
    private Short objIdSn;

    @Column(name = "unit_id", length = 40)
    private String unitId;

    @Column(name = "unit_nm", length = 20)
    private String unitNm;

    @Column(name = "stat_latest_chn_dt")
    private LocalDate statLatestChnDt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;
}