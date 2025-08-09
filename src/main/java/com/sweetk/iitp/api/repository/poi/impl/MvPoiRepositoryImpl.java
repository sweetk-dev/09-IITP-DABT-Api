package com.sweetk.iitp.api.repository.poi.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.config.DistanceCalculationConfig;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.MvPoi;
import com.sweetk.iitp.api.dto.poi.MvPoiLocation;
import com.sweetk.iitp.api.util.RepositoryUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MvPoiRepositoryImpl implements MvPoiRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(MvPoiRepositoryImpl.class);

    private final JPAQueryFactory queryFactory;
    private final ObjectMapper objectMapper;
    private final DistanceCalculationConfig distanceConfig;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;


    private static final String SQL_MV_CATEGORY_COL = "search_filter_json->'search_filter'";
    private static final String SQL_MV_ORDER_BY = " ORDER BY title ";
    private static final String SQL_ORDER_BY_DISTANCE = " ORDER BY distance ";

    private static final String SQL_MV_BASE_COLUMNS = "poi_id, language_code, title, summary, basic_info, " +
                                                        "address_code, address_road, address_detail, latitude, longitude, " +
                                                        "detail_json, search_filter_json";

    private static final String SQL_MV_TOT_CNT_COLUMN =  " COUNT(*) OVER() AS total_count ";

    private static final String SQL_MV_BASE_FROM = " FROM mv_poi WHERE is_published = 'Y' AND is_deleted = 'N' ";

    private static final String SQL_MV_DTO_QUERY = "SELECT " + SQL_MV_BASE_COLUMNS + " " +
                                SQL_MV_BASE_FROM;


    private static final String SQL_MV_DTO_QUERY_WITH_COUNT = "SELECT " + SQL_MV_BASE_COLUMNS + ", " +
                                SQL_MV_TOT_CNT_COLUMN +
                                SQL_MV_BASE_FROM;


    // 위치 검색용 기본 쿼리 (거리 계산 포함)
    private final String SQL_MV_LOCATION_QUERY;
    private final String SQL_MV_LOCATION_QUERY_WITH_COUNT;

    public MvPoiRepositoryImpl(JPAQueryFactory queryFactory, DistanceCalculationConfig distanceConfig) {
        this.queryFactory = queryFactory;
        this.distanceConfig = distanceConfig;
        this.objectMapper = new ObjectMapper();
        
        // 기본 위치 검색 쿼리 생성
        this.SQL_MV_LOCATION_QUERY = "SELECT " + SQL_MV_BASE_COLUMNS + ", " +
                            distanceConfig.getDistanceCalculationSql() + " AS distance " +
                            SQL_MV_BASE_FROM;

        this.SQL_MV_LOCATION_QUERY_WITH_COUNT = "SELECT " + SQL_MV_BASE_COLUMNS + ", " +
                        distanceConfig.getDistanceCalculationSql() + " AS distance, " +
                        SQL_MV_TOT_CNT_COLUMN +
                        SQL_MV_BASE_FROM;
    }

    public Optional<MvPoi> findByIdWithPublished(Long poiId) {
        StringBuilder sql = new StringBuilder(SQL_MV_DTO_QUERY).append(" AND poi_id = ? ");

        log.debug("[MvPoi] ID별 조회 쿼리: {}", sql);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setLong(1, poiId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    MvPoi poi = setMvPoi(rs);
                    return Optional.of(poi);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("[MvPoi] ID별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
    }


    public List<MvPoi> findAllPublished() {
        StringBuilder sql = new StringBuilder(SQL_MV_DTO_QUERY).append(SQL_MV_ORDER_BY);
        log.debug("[MvPoi] 전체 조회 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MvPoi poi = setMvPoi(rs);
                    entityList.add(poi);
                }
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 전체 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return entityList;
    }

    public PoiPageResult findAllWithPagingCount(int offset, int size) {
        StringBuilder sql = new StringBuilder(SQL_MV_DTO_QUERY).append(SQL_MV_ORDER_BY);
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);
        log.debug("[MvPoi] 전체 조회 쿼리(with count): {}", sql);

        long totalCount = 0L;
        List<MvPoi> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }

                    MvPoi poi = setMvPoi(rs);
                    entityList.add(poi);
                }
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 전체 조회 쿼리(with count) 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return new PoiPageResult(entityList, totalCount);
    }


    public List<MvPoi> findByCategoryType(String categoryType) {
        StringBuilder sql = new StringBuilder(SQL_MV_DTO_QUERY)
                .append(" AND (")
                .append(SQL_MV_ORDER_BY)
                .append(" ? '")
                .append(RepositoryUtils.escapeSql(categoryType))
                .append("')")
                .append(SQL_MV_ORDER_BY);

        log.debug("[MvPoi] 카테고리별 조회 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MvPoi poi = setMvPoi(rs);
                    entityList.add(poi);
                }
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 카테고리별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return entityList;
    }



    public PoiPageResult findByCategoryTypeWithPagingCount(String categoryType, int offset, int size) {
        StringBuilder sql = new StringBuilder(SQL_MV_DTO_QUERY)
                .append(" AND (")
                .append(SQL_MV_ORDER_BY)
                .append(" ? '")
                .append(RepositoryUtils.escapeSql(categoryType))
                .append("')")
                .append(SQL_MV_ORDER_BY);
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);

        log.debug("[MvPoi] 카테고리별(with count) 조회 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        long totalCount = 0L;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                    MvPoi poi = setMvPoi(rs);
                    entityList.add(poi);
                }
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 카테고리별(with count) 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return new PoiPageResult(entityList, totalCount);
    }



    public List<MvPoi> findByCategoryAndSubCate(
            String category, String subCate, String name
    ) {
        StringBuilder sql = getQueryFindByCategorySubCate(SQL_MV_DTO_QUERY_WITH_COUNT,category, subCate, name);

        log.debug("[MvPoi] 카테고리 검색 실행 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MvPoi poi = setMvPoi(rs);
                    entityList.add(poi);
                }
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 카테고리 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return entityList;
    }


    public PoiPageResult findByCategoryAndSubCateWithPagingCount(
            String category, String subCate, String name, int offset, int size
    ) {
        StringBuilder sql = getQueryFindByCategorySubCate(SQL_MV_DTO_QUERY_WITH_COUNT,category, subCate, name);
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);

        log.debug("[MvPoi] 카테고리 검색(with count) 실행 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        long totalCount = 0L;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                    MvPoi poi = setMvPoi(rs);
                    entityList.add(poi);
                }
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 카테고리 검색(with count) 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return new PoiPageResult(entityList, totalCount);
    }





    // 거리 정보를 포함한 위치 기반 검색
    public List<MvPoiLocation> findByLocationWithConditions(String category, String name,
                                                    BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {

        StringBuilder sql = new StringBuilder(SQL_MV_LOCATION_QUERY);
        sql = getQueryFindByLocCategory(sql, category, name);
        sql.append(distanceConfig.getDistanceFilterSql(latitude, longitude, radius.multiply(new BigDecimal(1000))));
        sql.append(SQL_ORDER_BY_DISTANCE);

        log.debug("[MvPoi] 거리 정보 포함 위치 기반 검색 실행 쿼리: {}", sql);
        
        List<MvPoiLocation> entityList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            // 거리 계산을 위한 파라미터 설정 (longitude, latitude)
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MvPoiLocation poi = setMvPoiLocation(rs);
                    entityList.add(poi);
                }
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 거리 정보 포함 위치 기반 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }

        return entityList;
    }

    public PoiPageResult<MvPoiLocation> findByLocationWithConditionsAndPagingCount(String category, String name,
                                                                           BigDecimal latitude, BigDecimal longitude, BigDecimal radius, int offset, int size) {

        StringBuilder sql = new StringBuilder(SQL_MV_LOCATION_QUERY_WITH_COUNT);
        sql = getQueryFindByLocCategory(sql, category, name);
        sql.append(distanceConfig.getDistanceFilterSql(latitude, longitude, radius.multiply(new BigDecimal(1000))));
        sql.append(SQL_ORDER_BY_DISTANCE);
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);

        log.debug("[MvPoi] 거리 정보 포함 위치 기반 검색(with count) 실행 쿼리: {}", sql);

        List<MvPoiLocation> entityList = new ArrayList<>();
        long totalCount = 0;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            // 거리 계산을 위한 파라미터 설정 (longitude, latitude)
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                    MvPoiLocation poi = setMvPoiLocation(rs);
                    entityList.add(poi);
                }
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 거리 정보 포함 위치 기반(with count) 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }

        return new PoiPageResult<>(entityList, totalCount);
    }



    private StringBuilder getQueryFindByCategorySubCate(String baseQuery, String category, String subCate, String name) {
        StringBuilder sql = new StringBuilder(baseQuery);
        boolean hasCategory = category != null && !category.isEmpty();
        boolean hasSubCate = subCate != null && !subCate.isEmpty();
        boolean hasName = name != null && !name.isEmpty();

        if (hasCategory) {
            category = RepositoryUtils.escapeSql(category);
            sql.append("AND ("+ SQL_MV_CATEGORY_COL +" ? '").append(category).append("') ");
            if (hasSubCate) {
                String subItem = "";
                if (subCate.contains(",")) {
                    String[] subCates = subCate.split(",");
                    sql.append("AND (");
                    for (int i = 0; i < subCates.length; i++) {
                        if (i > 0) sql.append(" OR ");
                        subItem = RepositoryUtils.escapeSql(subCates[i].trim());
                        sql.append(SQL_MV_CATEGORY_COL + "->>'").append(category).append("' = '").append(subItem).append("' ");
                    }
                    sql.append(") ");
                } else {
                    subItem = RepositoryUtils.escapeSql(subCate.trim());
                    sql.append("AND "+ SQL_MV_CATEGORY_COL +"->>'").append(category).append("' LIKE '%").append(subItem).append("%' ");
                }
            }
        }
        if (hasName) {
            name =  RepositoryUtils.escapeSql(name);
            sql.append("AND title LIKE '%").append(name).append("%' ");
        }
        sql.append(SQL_MV_ORDER_BY);

        return sql;
    }


    private StringBuilder getQueryFindByLocCategory(StringBuilder sql, String category, String name) {
        if (category != null && !category.isEmpty()) {
            category = RepositoryUtils.escapeSql(category);
            sql.append("AND (").append(SQL_MV_CATEGORY_COL).append(" ? '").append(category).append("') ");
        }
        if (name != null && !name.isEmpty()) {
            name = RepositoryUtils.escapeSql(name);
            sql.append("AND title LIKE '%").append(name).append("%' ");
        }
        return sql;
    }





    private MvPoi setMvPoi(ResultSet rs) throws SQLException {
        MvPoi poi = new MvPoi();
        poi.setPoiId(rs.getLong("poi_id"));
        poi.setLanguageCode(rs.getString("language_code"));
        poi.setTitle(rs.getString("title"));
        poi.setSummary(rs.getString("summary"));
        poi.setBasicInfo(rs.getString("basic_info"));
        poi.setAddressCode(rs.getString("address_code"));
        poi.setAddressRoad(rs.getString("address_road"));
        poi.setAddressDetail(rs.getString("address_detail"));
        poi.setLatitude(rs.getBigDecimal("latitude"));
        poi.setLongitude(rs.getBigDecimal("longitude"));
        poi.setDetailJson(rs.getString("detail_json"));
        poi.setSearchFilterJson(rs.getString("search_filter_json"));
        return poi;
    }

        private MvPoiLocation setMvPoiLocation(ResultSet rs) throws SQLException {
        // 직접 Location 객체 생성 (메모리 효율성)
        return new MvPoiLocation(
            rs.getLong("poi_id"),
            rs.getString("language_code"),
            rs.getString("title"),
            rs.getString("summary"),
            rs.getString("basic_info"),
            rs.getString("address_code"),
            rs.getString("address_road"),
            rs.getString("address_detail"),
            rs.getBigDecimal("latitude"),
            rs.getBigDecimal("longitude"),
            rs.getString("detail_json"),
            rs.getString("search_filter_json"),
            rs.getInt("distance")
        );
    }
}
