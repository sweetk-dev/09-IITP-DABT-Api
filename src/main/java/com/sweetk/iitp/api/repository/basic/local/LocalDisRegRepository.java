package com.sweetk.iitp.api.repository.basic.local;

import com.sweetk.iitp.api.dto.basic.local.LocalDisRegSummary.Part;
import com.sweetk.iitp.api.repository.support.JdbcReadSupport;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.sweetk.iitp.api.repository.support.JdbcReadSupport.*;

/**
 * 시군 단위 등록장애인 합산 조회 — 경기 읍면동 집계(최신 판)와 KOSIS 시군구별(DT_11761_N009).
 *
 * <p>KOSIS 시군구 코드는 메타(stats_kosis_metadata_code, 분류 HJG)의 이름으로 찾는다. 경기도(상위 코드 31) 아래에서
 * 이름이 시군명과 같거나 시군명으로 시작하는 항목(예: 안양시 → 안양시만안구·안양시동안구)을 더한다.
 * KOSIS 표에는 구가 있는 시의 시 합계 행이 없다.
 */
@Repository
public class LocalDisRegRepository {

    public static final String KOSIS_SOURCE_NAME = "KOSIS 장애인현황(보건복지부) — 시군구별,장애정도별,성별 등록장애인수";
    private static final String GYEONGGI_KOSIS_CODE = "31";

    private final JdbcReadSupport db;

    public LocalDisRegRepository(JdbcReadSupport db) {
        this.db = db;
    }

    // ---- 경기 읍면동 집계 --------------------------------------------------------
    public LocalDate ggLatestBaseDt(String sigun) {
        // 집계 결과는 행이 1개라도 값이 NULL 일 수 있다 — Optional 에 NULL 을 담지 않도록 목록으로 받는다.
        List<LocalDate> r = db.list("SELECT max(base_dt) AS d FROM v_stats_gg_dis_reg_emd_latest WHERE sigun_nm = ?",
                List.of(sigun), (rs, n) -> date(rs, "d"));
        return r.isEmpty() ? null : r.get(0);
    }

    /** 구별 합계 — 구가 없는 시군은 이름 null 인 1행 */
    public List<Part> ggByGu(String sigun) {
        return db.list("SELECT gu_nm AS name, sum(tot_cnt) AS t, sum(sev_cnt) AS s, sum(nsev_cnt) AS ns, " +
                        "sum(male_cnt) AS m, sum(female_cnt) AS f, CAST(count(DISTINCT emd_nm) AS int) AS emd " +
                        "FROM v_stats_gg_dis_reg_emd_latest WHERE sigun_nm = ? GROUP BY gu_nm ORDER BY gu_nm NULLS FIRST",
                List.of(sigun), (rs, n) -> part(rs, true));
    }

    public List<Part> ggByType(String sigun) {
        return db.list("SELECT dis_type AS name, sum(tot_cnt) AS t, sum(sev_cnt) AS s, sum(nsev_cnt) AS ns, " +
                        "sum(male_cnt) AS m, sum(female_cnt) AS f FROM v_stats_gg_dis_reg_emd_latest " +
                        "WHERE sigun_nm = ? GROUP BY dis_type ORDER BY sum(tot_cnt) DESC NULLS LAST, dis_type",
                List.of(sigun), (rs, n) -> part(rs, false));
    }

    // ---- KOSIS 시군구별 ---------------------------------------------------------
    private static final String KOSIS_CODES =
            "WITH src AS (SELECT src_data_id, CAST(stat_latest_chn_dt AS date) AS d FROM stats_src_data_info " +
            "              WHERE intg_tbl_id = 'stats_dis_reg_sgg_by_sev_gen' AND del_yn = 'N' ORDER BY src_data_id DESC LIMIT 1), " +
            "codes AS (SELECT m.itm_id, m.itm_nm, s.src_data_id FROM stats_kosis_metadata_code m JOIN src s " +
            "            ON m.src_data_id = s.src_data_id AND m.stat_latest_chn_dt = s.d " +
            "         WHERE m.obj_id = 'HJG' AND m.up_itm_id = '" + GYEONGGI_KOSIS_CODE + "' " +
            "           AND (m.itm_nm = ? OR m.itm_nm LIKE ? ESCAPE '\\')) ";

    public Integer kosisLatestYear(String sigun) {
        List<Integer> r = db.list(KOSIS_CODES + "SELECT CAST(max(d.prd_de) AS int) AS y FROM stats_dis_reg_sgg_by_sev_gen d " +
                        "JOIN codes c ON d.src_data_id = c.src_data_id AND d.c1 = c.itm_id",
                codeParams(sigun), (rs, n) -> i(rs, "y"));
        return r.isEmpty() ? null : r.get(0);
    }

    /**
     * 연도 1개의 구(시군구 코드)별 값. 장애정도 C2: TT 합계·01 심한·02 심하지않은, 성별 itm_id: T001 계·T002 남·T003 여.
     * 반환 행 이름은 시군명을 뗀 구명(구가 없으면 시군명 그대로).
     */
    public List<long[]> kosisRaw(String sigun, int year, List<String> names) {
        List<Object> p = new ArrayList<>(codeParams(sigun));
        p.add(year);
        return db.list(KOSIS_CODES +
                        "SELECT c.itm_nm, " +
                        "sum(CASE WHEN d.c2 = 'TT' AND d.itm_id = 'T001' THEN d.dt END) AS t, " +
                        "sum(CASE WHEN d.c2 = '01' AND d.itm_id = 'T001' THEN d.dt END) AS s, " +
                        "sum(CASE WHEN d.c2 = '02' AND d.itm_id = 'T001' THEN d.dt END) AS ns, " +
                        "sum(CASE WHEN d.c2 = 'TT' AND d.itm_id = 'T002' THEN d.dt END) AS m, " +
                        "sum(CASE WHEN d.c2 = 'TT' AND d.itm_id = 'T003' THEN d.dt END) AS f " +
                        "FROM stats_dis_reg_sgg_by_sev_gen d JOIN codes c ON d.src_data_id = c.src_data_id AND d.c1 = c.itm_id " +
                        "WHERE d.prd_de = ? GROUP BY c.itm_nm ORDER BY c.itm_nm",
                p, (rs, n) -> {
                    names.add(rs.getString("itm_nm"));
                    return new long[]{lng(rs, "t"), lng(rs, "s"), lng(rs, "ns"), lng(rs, "m"), lng(rs, "f")};
                });
    }

    private static List<Object> codeParams(String sigun) {
        String esc = sigun.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
        return List.of(sigun, esc + "%");
    }

    private static long lng(ResultSet rs, String c) throws SQLException {
        java.math.BigDecimal v = rs.getBigDecimal(c);
        return v == null ? 0L : v.longValue();
    }

    private static Long lngOrNull(ResultSet rs, String c) throws SQLException {
        long v = rs.getLong(c);
        return rs.wasNull() ? null : v;
    }

    private static Part part(ResultSet rs, boolean withEmd) throws SQLException {
        Part x = new Part();
        x.setMaleCnt(lngOrNull(rs, "m"));
        x.setFemaleCnt(lngOrNull(rs, "f"));
        x.setName(s(rs, "name"));
        x.setTotalCnt(lngOrNull(rs, "t"));
        x.setSevCnt(lngOrNull(rs, "s"));
        x.setNsevCnt(lngOrNull(rs, "ns"));
        if (withEmd) {
            x.setEmdCount(i(rs, "emd"));
        }
        return x;
    }
}
