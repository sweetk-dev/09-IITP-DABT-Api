package com.sweetk.iitp.api.repository.basic.local;

import com.sweetk.iitp.api.dto.basic.local.GgDisRegEdition;
import com.sweetk.iitp.api.dto.basic.local.GgDisRegEmd;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.repository.support.JdbcReadSupport;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.sweetk.iitp.api.repository.support.JdbcReadSupport.*;

/**
 * 경기도 읍면동별 등록장애인 (stats_gg_dis_reg_emd) 조회.
 * 시군마다 원천 기준일이 달라 판(base_dt)을 쌓아 둔다. 기본 조회는 시군별 최신 판 뷰(v_stats_gg_dis_reg_emd_latest).
 */
@Repository
public class GgDisRegEmdRepository {

    public static final String SOURCE_NAME = "경기도 내 등록장애인 집계현황(읍면동별·유형별·정도별, 경기데이터드림)";

    private static final String COLUMNS =
            "id, base_dt, sigun_nm, gu_nm, emd_nm, dis_type, tot_cnt, male_cnt, female_cnt, sev_cnt, sev_male_cnt, " +
            "sev_female_cnt, nsev_cnt, nsev_male_cnt, nsev_female_cnt";

    private static final RowMapper<GgDisRegEmd> MAPPER = (rs, n) -> {
        GgDisRegEmd r = new GgDisRegEmd();
        r.setBaseDt(date(rs, "base_dt"));
        r.setSigunName(s(rs, "sigun_nm"));
        r.setGuName(s(rs, "gu_nm"));
        r.setEmdName(s(rs, "emd_nm"));
        r.setDisType(s(rs, "dis_type"));
        r.setTotCnt(i(rs, "tot_cnt"));
        r.setMaleCnt(i(rs, "male_cnt"));
        r.setFemaleCnt(i(rs, "female_cnt"));
        r.setSevCnt(i(rs, "sev_cnt"));
        r.setSevMaleCnt(i(rs, "sev_male_cnt"));
        r.setSevFemaleCnt(i(rs, "sev_female_cnt"));
        r.setNsevCnt(i(rs, "nsev_cnt"));
        r.setNsevMaleCnt(i(rs, "nsev_male_cnt"));
        r.setNsevFemaleCnt(i(rs, "nsev_female_cnt"));
        r.setSourceName(SOURCE_NAME);
        return r;
    };

    private final JdbcReadSupport db;

    public GgDisRegEmdRepository(JdbcReadSupport db) {
        this.db = db;
    }

    public PoiPageResult<GgDisRegEmd> search(String sigunName, String guName, String emdName, String disType,
                                             LocalDate baseDt, int offset, int size) {
        StringBuilder w = new StringBuilder(baseDt == null
                ? " FROM v_stats_gg_dis_reg_emd_latest WHERE 1=1 "
                : " FROM stats_gg_dis_reg_emd WHERE base_dt = ? ");
        List<Object> p = new ArrayList<>();
        if (baseDt != null) { p.add(baseDt); }
        if (has(sigunName)) { w.append("AND sigun_nm = ? "); p.add(sigunName.trim()); }
        if (has(guName)) { w.append("AND gu_nm = ? "); p.add(guName.trim()); }
        if (has(emdName)) { w.append("AND emd_nm ILIKE ? "); p.add(contains(emdName)); }
        if (has(disType)) { w.append("AND dis_type = ? "); p.add(disType.trim()); }
        return db.page(COLUMNS, w.toString(), p, "ORDER BY sigun_nm, gu_nm NULLS FIRST, emd_nm, dis_type, id",
                offset, size, MAPPER);
    }

    public List<GgDisRegEdition> editions(String sigunName) {
        StringBuilder sql = new StringBuilder(
                "SELECT e.sigun_nm, e.base_dt, CAST(count(*) AS int) AS row_count, sum(e.tot_cnt) AS total_cnt, " +
                "(e.base_dt = max(e.base_dt) OVER (PARTITION BY e.sigun_nm)) AS latest " +
                "FROM stats_gg_dis_reg_emd e ");
        List<Object> p = new ArrayList<>();
        if (has(sigunName)) { sql.append("WHERE e.sigun_nm = ? "); p.add(sigunName.trim()); }
        sql.append("GROUP BY e.sigun_nm, e.base_dt ORDER BY e.sigun_nm, e.base_dt DESC");
        return db.list(sql.toString(), p, (rs, n) -> new GgDisRegEdition(
                s(rs, "sigun_nm"), date(rs, "base_dt"), i(rs, "row_count"),
                rs.getObject("total_cnt") == null ? null : rs.getLong("total_cnt"), rs.getBoolean("latest")));
    }
}
