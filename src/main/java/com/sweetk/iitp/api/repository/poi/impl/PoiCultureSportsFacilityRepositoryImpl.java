package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.config.DistanceCalculationConfig;
import com.sweetk.iitp.api.constant.poi.PoiCultureSportsType;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacility;
import com.sweetk.iitp.api.dto.poi.PoiCultureSportsFacilityLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 장애인 문화·체육 시설 조회.
 *
 * <p>검색 조건 값은 모두 바인딩 변수로 넘긴다. 위치 검색은 거리 계산 식을 서브쿼리에서 한 번만
 * 계산하고 바깥에서 반경으로 거른다.
 */
@Slf4j
@Repository
public class PoiCultureSportsFacilityRepositoryImpl implements PoiCultureSportsFacilityRepositoryCustom {

    private static final String COLUMNS =
            "facility_id, facility_type, sido_code, sigun_cd, sigun_nm, name, addr_road, addr_jibun, " +
            "zip_code, latitude, longitude, tel, homepage, install_report_dt, corp_name, " +
            "(updated_at AT TIME ZONE 'Asia/Seoul')::date AS base_dt";

    private static final String BASE_FROM = " FROM poi_culture_sports_facility WHERE del_yn = 'N' ";

    private final DataSource dataSource;
    private final DistanceCalculationConfig distanceConfig;

    public PoiCultureSportsFacilityRepositoryImpl(DataSource dataSource, DistanceCalculationConfig distanceConfig) {
        this.dataSource = dataSource;
        this.distanceConfig = distanceConfig;
    }

    @Override
    public Optional<PoiCultureSportsFacility> findByIdToDto(Integer facilityId) {
        String sql = "SELECT " + COLUMNS + BASE_FROM + "AND facility_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(facilityId);
        List<PoiCultureSportsFacility> rows = query(sql, params, false).getContent();
        return rows.isEmpty() ? Optional.empty() : Optional.of(rows.get(0));
    }

    @Override
    public PoiPageResult<PoiCultureSportsFacility> findByConditions(String facilityType, String name,
                                                                    String sigunNm, String sidoCode,
                                                                    Integer offset, Integer size) {
        StringBuilder sql = new StringBuilder("SELECT ").append(COLUMNS)
                .append(", COUNT(*) OVER() AS total_count").append(BASE_FROM);
        List<Object> params = new ArrayList<>();
        appendConditions(sql, params, facilityType, name, sigunNm, sidoCode);
        sql.append(" ORDER BY facility_type, name, facility_id");
        appendPaging(sql, params, offset, size);
        return query(sql.toString(), params, true);
    }

    @Override
    public PoiPageResult<PoiCultureSportsFacilityLocation> findByLocation(Double latitude, Double longitude,
                                                                          Double radiusMeters,
                                                                          String facilityType, String name,
                                                                          Integer offset, Integer size) {
        // 거리 식의 물음표 2개는 1번=경도, 2번=위도 (DistanceCalculationConfig 참고)
        StringBuilder inner = new StringBuilder("SELECT ").append(COLUMNS).append(", ")
                .append(distanceConfig.getDistanceCalculationSql()).append(" AS distance")
                .append(BASE_FROM).append("AND latitude IS NOT NULL AND longitude IS NOT NULL ");
        List<Object> params = new ArrayList<>();
        params.add(longitude);
        params.add(latitude);
        appendConditions(inner, params, facilityType, name, null, null);

        StringBuilder sql = new StringBuilder("SELECT t.*, COUNT(*) OVER() AS total_count FROM (")
                .append(inner).append(") t WHERE t.distance <= ? ORDER BY t.distance, t.facility_id");
        params.add(radiusMeters);
        appendPaging(sql, params, offset, size);

        List<PoiCultureSportsFacilityLocation> list = new ArrayList<>();
        long total = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            bind(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new PoiCultureSportsFacilityLocation(toDto(rs), (int) Math.round(rs.getDouble("distance"))));
                    if (total == 0) {
                        total = rs.getLong("total_count");
                    }
                }
            }
        } catch (SQLException e) {
            log.error("[PoiCultureSportsFacility] 위치 기반 검색 중 오류", e);
            throw new RuntimeException("Database location query failed", e);
        }
        return new PoiPageResult<>(list, total);
    }

    private void appendConditions(StringBuilder sql, List<Object> params,
                                  String facilityType, String name, String sigunNm, String sidoCode) {
        if (facilityType != null) {
            sql.append("AND facility_type = ? ");
            params.add(facilityType);
        }
        if (name != null) {
            sql.append("AND name ILIKE ? ");
            params.add("%" + escapeLike(name.trim()) + "%");
        }
        if (sigunNm != null) {
            sql.append("AND sigun_nm ILIKE ? ");
            params.add("%" + escapeLike(sigunNm.trim()) + "%");
        }
        if (sidoCode != null) {
            sql.append("AND sido_code = ? ");
            params.add(sidoCode);
        }
    }

    private void appendPaging(StringBuilder sql, List<Object> params, Integer offset, Integer size) {
        if (size != null) {
            sql.append(" OFFSET ? LIMIT ?");
            params.add(offset == null ? 0 : offset);
            params.add(size);
        }
    }

    private PoiPageResult<PoiCultureSportsFacility> query(String sql, List<Object> params, boolean withCount) {
        List<PoiCultureSportsFacility> list = new ArrayList<>();
        long total = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            bind(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(toDto(rs));
                    if (withCount && total == 0) {
                        total = rs.getLong("total_count");
                    }
                }
            }
        } catch (SQLException e) {
            log.error("[PoiCultureSportsFacility] 조회 중 오류", e);
            throw new RuntimeException("Database query failed", e);
        }
        return new PoiPageResult<>(list, withCount ? total : list.size());
    }

    private static void bind(PreparedStatement ps, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object v = params.get(i);
            if (v instanceof Integer n) {
                ps.setInt(i + 1, n);
            } else if (v instanceof Double d) {
                ps.setDouble(i + 1, d);
            } else if (v instanceof String s) {
                ps.setString(i + 1, s);
            } else {
                ps.setObject(i + 1, v);
            }
        }
    }

    /** ILIKE 패턴 문자(%, _, \\)를 글자 그대로 찾도록 이스케이프한다. */
    private static String escapeLike(String s) {
        return s.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
    }

    private static PoiCultureSportsFacility toDto(ResultSet rs) throws SQLException {
        String type = rs.getString("facility_type");
        Date installDt = rs.getDate("install_report_dt");
        Date baseDt = rs.getDate("base_dt");
        return new PoiCultureSportsFacility(
                rs.getInt("facility_id"),
                type,
                PoiCultureSportsType.displayNameOf(type),
                rs.getString("sido_code"),
                rs.getString("sigun_cd"),
                rs.getString("sigun_nm"),
                rs.getString("name"),
                rs.getString("addr_road"),
                rs.getString("addr_jibun"),
                rs.getString("zip_code"),
                rs.getObject("latitude", Double.class),
                rs.getObject("longitude", Double.class),
                rs.getString("tel"),
                rs.getString("homepage"),
                installDt == null ? null : installDt.toLocalDate(),
                rs.getString("corp_name"),
                baseDt == null ? null : baseDt.toLocalDate()
        );
    }
}
