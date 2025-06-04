package com.sweetk.iitp.api.entity.basic;

import com.sweetk.iitp.api.entity.sys.SysExtApiInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 통계성 데이터 수집 소스 데이터 정보
 */
@Entity
@Table(name = "stats_src_data_info")
@Getter
@Setter
@NoArgsConstructor
public class StatsSrcDataInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "src_data_id")
    private Integer srcDataId;

    @ManyToOne
    @JoinColumn(name = "ext_api_id", nullable = false)
    private SysExtApiInfo extApiInfo;

    @Column(name = "ext_sys", length = 10)
    private String extSys;

    @Column(name = "intg_tbl_id", length = 50, nullable = false)
    private String intgTblId;

    @Column(name = "stat_title", length = 300, nullable = false)
    private String statTitle;

    @Column(name = "stat_org_id", length = 12, nullable = false)
    private String statOrgId;

    @Column(name = "stat_survey_name", length = 300, nullable = false)
    private String statSurveyName;

    @Column(name = "stat_pub_dt", length = 12, nullable = false)
    private String statPubDt;

    @Column(name = "periodicity", length = 10, nullable = false)
    private String periodicity;

    @Column(name = "collect_start_dt", length = 12, nullable = false)
    private String collectStartDt;

    @Column(name = "collect_end_dt", length = 12, nullable = false)
    private String collectEndDt;

    @Column(name = "stat_tbl_id", length = 40, nullable = false)
    private String statTblId;

    @Column(name = "stat_tbl_name", length = 300, nullable = false)
    private String statTblName;

    @Column(name = "stat_latest_chn_dt", length = 12, nullable = false)
    private String statLatestChnDt;

    @Column(name = "stat_data_ref_dt", length = 12)
    private String statDataRefDt;

    @Column(name = "avail_cat_cols", length = 40)
    private String availCatCols;


    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by", length = 40, nullable = false)
    private String createdBy;

    @Column(name = "updated_by", length = 40)
    private String updatedBy;

    @Column(name = "deleted_by", length = 40)
    private String deletedBy;

    @Transient
    //@JsonIgnore
    private String statOrgName;


    public List<String> getCategoryList(String categories ) {

        if (categories == null) {
            return null;
        }

        String[] categoryArr = categories.split(",");
        return List.of(categoryArr);
    }
}