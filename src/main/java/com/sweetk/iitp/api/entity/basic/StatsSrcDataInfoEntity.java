package com.sweetk.iitp.api.entity.basic;

import com.sweetk.iitp.api.constant.DataStatusType;
import com.sweetk.iitp.api.constant.DataStatusTypeConverter;
import com.sweetk.iitp.api.constant.SysConstants;
import com.sweetk.iitp.api.entity.sys.SysCommonCodeEntity;
import com.sweetk.iitp.api.entity.sys.SysExtApiInfoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * 통계성 데이터 수집 소스 데이터 정보
 */
@Entity
@Table(name = "stats_src_data_info")
@Getter
@Setter
@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL")
public class StatsSrcDataInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "src_data_id")
    private Integer srcDataId;

    @ManyToOne
    @JoinColumn(name = "ext_api_id", nullable = false)
    private SysExtApiInfoEntity extApiInfo;

    @Column(name = "ext_sys", length = 10)
    private String extSys;

    @Column(name = "stat_api_id",  nullable = false)
    private Integer statApiId;

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

    @Convert(converter = DataStatusTypeConverter.class)
    @Column(name = "status", length = 1, nullable = false)
    private DataStatusType status =  DataStatusType.ACTIVE;


    @Column(name = "del_yn", length = 1, nullable = false)
    private String delYn = SysConstants.YN_N;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "created_by", length = 40, nullable = false)
    private String createdBy;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "updated_by", length = 40)
    private String updatedBy;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @Column(name = "deleted_by", length = 40)
    private String deletedBy;

    @Transient
    private String statOrgName;

    @Transient
    private SysCommonCodeEntity statOrg;

    @PostLoad
    public void postLoad() {
        if (statOrg != null) {
            this.statOrgName = statOrg.getCodeNm();
        }
    }

    public List<String> getStingToList(String data ) {
        if (data == null) {
            return null;
        }
        String[] StringArr = data.split(",");
        return List.of(StringArr);
    }

    public List<String> getAvailCatColsList() {
        return getStingToList(availCatCols);
    }

    public Integer toIntCollectStartDt () {
        return Integer.parseInt(this.collectStartDt);
    }

    public Integer toIntCollectEndDt () {
        return Integer.parseInt(this.collectEndDt);
    }

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    /**
     * 데이터 논리적 삭제
     * @param deletedBy 삭제자
     */
    public void softDelete(String deletedBy) {
        this.status = DataStatusType.DELETED;
        this.deletedAt = OffsetDateTime.now();
        this.deletedBy = deletedBy;
    }

}