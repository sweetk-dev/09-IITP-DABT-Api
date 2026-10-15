package com.sweetk.iitp.api.repository.support;

import com.sweetk.iitp.api.config.DistanceCalculationConfig;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 읽기 전용 조회 공통 도구 — 바인딩 변수 조회, 페이징(총 건수 포함), 위치 기반(거리순) 조회.
 *
 * <p>모든 조건 값은 바인딩 변수로 넘긴다. {@code fromWhere} 는 {@code " FROM 테이블 WHERE ... "} 형태로
 * 받고, 그 안의 물음표 값은 {@code params} 순서대로 바인딩한다.
 */
@Component
public class JdbcReadSupport {

    /** 페이지 크기 상한 */
    public static final int MAX_PAGE_SIZE = 1000;

    private final JdbcTemplate jdbc;
    private final DistanceCalculationConfig distanceConfig;

    public JdbcReadSupport(JdbcTemplate jdbc, DistanceCalculationConfig distanceConfig) {
        this.jdbc = jdbc;
        this.distanceConfig = distanceConfig;
    }

    public <T> List<T> list(String sql, List<Object> params, RowMapper<T> mapper) {
        return jdbc.query(sql, mapper, params.toArray());
    }

    public <T> Optional<T> one(String sql, List<Object> params, RowMapper<T> mapper) {
        List<T> rows = list(sql, params, mapper);
        return rows.isEmpty() ? Optional.empty() : Optional.of(rows.get(0));
    }

    /** 일반 페이징 조회. 페이지가 범위를 넘어 결과가 없으면 총 건수를 따로 센다. */
    public <T> PoiPageResult<T> page(String columns, String fromWhere, List<Object> params, String orderBy,
                                     int offset, int size, RowMapper<T> mapper) {
        checkPaging(offset, size);
        String sql = "SELECT " + columns + ", COUNT(*) OVER() AS total_count " + fromWhere + " " + orderBy + " OFFSET ? LIMIT ?";
        List<Object> p = new ArrayList<>(params);
        p.add(offset);
        p.add(size);
        long[] total = {0};
        List<T> rows = jdbc.query(sql, (rs, n) -> {
            if (n == 0) {
                total[0] = rs.getLong("total_count");
            }
            return mapper.mapRow(rs, n);
        }, p.toArray());
        if (rows.isEmpty() && offset > 0) {
            Long c = jdbc.queryForObject("SELECT COUNT(*) " + fromWhere, Long.class, params.toArray());
            total[0] = c == null ? 0 : c;
        }
        return new PoiPageResult<>(rows, total[0]);
    }

    /**
     * 위치 기반 페이징 조회(반경 단위: 미터, 가까운 순). 거리 식은 서브쿼리에서 한 번만 계산한다.
     * {@code columns} 에는 latitude, longitude 가 있는 테이블의 컬럼을 준다. 같은 거리는 {@code idColumn} 순으로 정렬한다.
     */
    public <T> PoiPageResult<T> locationPage(String columns, String fromWhere, List<Object> params, String idColumn,
                                             double latitude, double longitude, double radiusMeters,
                                             int offset, int size, RowMapper<T> mapper) {
        checkPaging(offset, size);
        // 거리 식의 물음표 2개: 1번=경도, 2번=위도 (DistanceCalculationConfig 참고)
        String inner = "SELECT " + columns + ", " + distanceConfig.getDistanceCalculationSql() + " AS distance "
                + fromWhere + " AND latitude IS NOT NULL AND longitude IS NOT NULL";
        List<Object> base = new ArrayList<>();
        base.add(longitude);
        base.add(latitude);
        base.addAll(params);
        base.add(radiusMeters);

        String sql = "SELECT t.*, COUNT(*) OVER() AS total_count FROM (" + inner + ") t "
                + "WHERE t.distance <= ? ORDER BY t.distance, t." + idColumn + " OFFSET ? LIMIT ?";
        List<Object> p = new ArrayList<>(base);
        p.add(offset);
        p.add(size);
        long[] total = {0};
        List<T> rows = jdbc.query(sql, (rs, n) -> {
            if (n == 0) {
                total[0] = rs.getLong("total_count");
            }
            return mapper.mapRow(rs, n);
        }, p.toArray());
        if (rows.isEmpty() && offset > 0) {
            Long c = jdbc.queryForObject("SELECT COUNT(*) FROM (" + inner + ") t WHERE t.distance <= ?",
                    Long.class, base.toArray());
            total[0] = c == null ? 0 : c;
        }
        return new PoiPageResult<>(rows, total[0]);
    }

    public static void checkPaging(int offset, int size) {
        if (offset < 0) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "page 값이 범위를 벗어났습니다.");
        }
        if (size > MAX_PAGE_SIZE) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE,
                    "size 는 %d 이하여야 합니다.".formatted(MAX_PAGE_SIZE));
        }
    }

    /** 부분 일치 검색 값. LIKE 패턴 문자(%, _, 역슬래시)는 글자 그대로 찾는다. */
    public static String contains(String s) {
        String e = s.trim().replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
        return "%" + e + "%";
    }

    public static boolean has(String s) {
        return s != null && !s.trim().isEmpty();
    }

    // ---- ResultSet 읽기 (NULL 보존) ----
    public static Integer i(ResultSet rs, String c) throws SQLException {
        return rs.getObject(c, Integer.class);
    }

    public static Long l(ResultSet rs, String c) throws SQLException {
        return rs.getObject(c, Long.class);
    }

    public static Double d(ResultSet rs, String c) throws SQLException {
        return rs.getObject(c, Double.class);
    }

    public static String s(ResultSet rs, String c) throws SQLException {
        return rs.getString(c);
    }

    public static LocalDate date(ResultSet rs, String c) throws SQLException {
        java.sql.Date v = rs.getDate(c);
        return v == null ? null : v.toLocalDate();
    }

    /** 위치 조회 결과일 때만 distance(미터, 반올림)를 돌려준다. */
    public static Integer distance(ResultSet rs) throws SQLException {
        java.sql.ResultSetMetaData md = rs.getMetaData();
        boolean found = false;
        for (int c = md.getColumnCount(); c >= 1 && !found; c--) {
            found = "distance".equalsIgnoreCase(md.getColumnLabel(c));
        }
        if (!found) {
            return null;
        }
        Double v = rs.getObject("distance", Double.class);
        return v == null ? null : (int) Math.round(v);
    }
}
