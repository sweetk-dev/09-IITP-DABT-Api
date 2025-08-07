package com.sweetk.iitp.api.repository.poi.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.MvPoiPageResult;
import com.sweetk.iitp.api.dto.poi.MvPoi;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MvPoiRepositoryImpl implements MvPoiRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(MvPoiRepositoryImpl.class);

    private final JPAQueryFactory queryFactory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;


    private static final String mvPoiCategoryColumn = "search_filter_json->'search_filter'";
    private static final String mvPoiOrderByCondition = "ORDER BY title";

    private static final String mvPoiDtoQuery = "SELECT " +
            "poi_id, " +
            "language_code, " +
            "title, summary, basic_info, address_code, address_road, address_detail, latitude, longitude, detail_json, search_filter_json " +
            "FROM mv_poi WHERE is_published = 'Y' AND is_deleted = 'N' ";


    //For Paging Query (total_count)
    private String addCountToQuery(String baseQuery) {
        return baseQuery.replace("SELECT ", "SELECT COUNT(*) OVER() AS total_count, ");
    }


    public Optional<MvPoi> findByIdWithPublished(Long poiId) {
        String mvPoiDtoQueryById = mvPoiDtoQuery  + " AND poi_id = ? ";

        log.debug("[MvPoi] ID별 조회 쿼리: {}", mvPoiDtoQueryById);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(mvPoiDtoQueryById)) {

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
        StringBuilder sql = new StringBuilder(mvPoiDtoQuery)
                .append(mvPoiOrderByCondition);

        log.debug("[MvPoi] 전체 조회 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {

            while (rs.next()) {
                MvPoi poi = setMvPoi(rs);
                entityList.add(poi);
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 전체 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return entityList;
    }

    public MvPoiPageResult findAllWithPagingCount(int offset, int size) {
        StringBuilder sql = new StringBuilder(mvPoiDtoQuery)
                .append(mvPoiOrderByCondition)
                .append(" OFFSET ").append(offset).append(" LIMIT ").append(size);

        log.debug("[MvPoi] 전체 조회 쿼리(with count): {}", sql);

        long totalCount = 0L;
        List<MvPoi> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {

            while (rs.next()) {
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }

                MvPoi poi = setMvPoi(rs);
                entityList.add(poi);
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 전체 조회 쿼리(with count) 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return new MvPoiPageResult(entityList, totalCount);
    }


    public List<MvPoi> findByCategoryType(String categoryType) {

        String conditionCategory = "AND (" + mvPoiCategoryColumn + " ? '" + categoryType + "') ";
        StringBuilder sql = new StringBuilder(mvPoiDtoQuery)
                .append(conditionCategory)
                .append(mvPoiOrderByCondition);

        log.debug("[MvPoi] 카테고리별 조회 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {

            while (rs.next()) {
                MvPoi poi = setMvPoi(rs);
                entityList.add(poi);
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 카테고리별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return entityList;
    }



    public MvPoiPageResult findByCategoryTypeWithPagingCount(String categoryType, int offset, int size) {
        String conditionCategory = "AND (" + mvPoiCategoryColumn + " ? '" + categoryType + "') ";
        StringBuilder sql = new StringBuilder(addCountToQuery(mvPoiDtoQuery))
                .append(conditionCategory)
                .append(mvPoiOrderByCondition)
                .append(" OFFSET ").append(offset).append(" LIMIT ").append(size);

        log.debug("[MvPoi] 카테고리별(with count) 조회 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        long totalCount = 0L;
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            while (rs.next()) {
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
                MvPoi poi = setMvPoi(rs);
                entityList.add(poi);
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 카테고리별(with count) 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return new MvPoiPageResult(entityList, totalCount);
    }



    public List<MvPoi> findByCategoryAndSubCate(
            String category, String subCate, String name
    ) {
        StringBuilder sql = setFindByCategorySubCateSql(addCountToQuery(mvPoiDtoQuery),category, subCate, name);

        log.debug("[MvPoi] 카테고리 검색 실행 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            while (rs.next()) {
                MvPoi poi = setMvPoi(rs);
                entityList.add(poi);
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 카테고리 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return entityList;
    }


    public MvPoiPageResult findByCategoryAndSubCateWithPagingCount(
            String category, String subCate, String name, int offset, int size
    ) {
        StringBuilder sql = setFindByCategorySubCateSql(addCountToQuery(mvPoiDtoQuery),category, subCate, name)
                                .append(" OFFSET ").append(offset).append(" LIMIT ").append(size);

        log.debug("[MvPoi] 카테고리 검색(with count) 실행 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        long totalCount = 0L;
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            while (rs.next()) {
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
                MvPoi poi = setMvPoi(rs);
                entityList.add(poi);
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 카테고리 검색(with count) 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return new MvPoiPageResult(entityList, totalCount);
    }



    public List<MvPoi> findByLocation( String category, String name,
                                                    BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {

        StringBuilder sql = setFindByLocationCategorySql(mvPoiDtoQuery, category, name, latitude, longitude, radius);

        log.debug("[MvPoi] 위치 기반 검색 실행 쿼리: {}", sql);
        
        List<MvPoi> entityList = new ArrayList<>();
        long totalCount = 0;

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            while (rs.next()) {
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
                MvPoi poi = setMvPoi(rs);
                entityList.add(poi);
            }
        } catch (SQLException e) {
            log.error("[MvPoi]  위치 기반 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }

        return entityList;
    }




    public MvPoiPageResult findByLocationWithPagingCount( String category, String name,
            BigDecimal latitude, BigDecimal longitude, BigDecimal radius, int offset, int size) {

        StringBuilder sql = setFindByLocationCategorySql(addCountToQuery(mvPoiDtoQuery), category, name, latitude, longitude, radius)
                .append(" OFFSET ").append(offset).append(" LIMIT ").append(size);

        log.debug("[MvPoi] 위치 기반 검색(with count) 실행 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        long totalCount = 0;

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            while (rs.next()) {
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
                MvPoi poi = setMvPoi(rs);
                entityList.add(poi);
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 위치 기반(with count) 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }

        return new MvPoiPageResult(entityList, totalCount);
    }



    private StringBuilder setFindByLocationCategorySql(String baseQuery, String category, String name, BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {
        StringBuilder sql = new StringBuilder(baseQuery);

        boolean hasCategory = category != null && !category.isEmpty();
        boolean hasName = name != null && !name.isEmpty();

        if (hasCategory) {
            sql.append("AND ("+ mvPoiCategoryColumn +" ? '").append(category).append("') ");
        }
        if (hasName) {
            sql.append("AND title LIKE '%").append(name.replace("'", "''")).append("%' ");
        }

        sql.append("AND ST_DWithin(geom, ST_GeomFromText('POINT(").append(longitude).append(" ").append(latitude).append(")'), ").append(radius.multiply(new java.math.BigDecimal(1000))).append(") ");
        sql.append(mvPoiOrderByCondition);

        return sql;
    }



    private StringBuilder setFindByCategorySubCateSql(String baseQuery, String category, String subCate, String name) {
        StringBuilder sql = new StringBuilder(baseQuery);
        boolean hasCategory = category != null && !category.isEmpty();
        boolean hasSubCate = subCate != null && !subCate.isEmpty();
        boolean hasName = name != null && !name.isEmpty();

        if (hasCategory) {
            sql.append("AND ("+ mvPoiCategoryColumn +" ? '").append(category).append("') ");
            if (hasSubCate) {
                if (subCate.contains(",")) {
                    String[] subCates = subCate.split(",");
                    sql.append("AND (");
                    for (int i = 0; i < subCates.length; i++) {
                        if (i > 0) sql.append(" OR ");
                        sql.append(mvPoiCategoryColumn + "->>'").append(category).append("' = '").append(subCates[i].trim().replace("'", "''")).append("' ");
                    }
                    sql.append(") ");
                } else {
                    sql.append("AND "+ mvPoiCategoryColumn +"->>'").append(category).append("' LIKE '%").append(subCate.replace("'", "''")).append("%' ");
                }
            }
        }
        if (hasName) {
            sql.append("AND title LIKE '%").append(name.replace("'", "''")).append("%' ");
        }
        sql.append(mvPoiOrderByCondition);

        return sql;
    }




    private MvPoi setMvPoi(ResultSet rs) throws SQLException {
        return new MvPoi(
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
                rs.getString("search_filter_json")
        );
    }
}
