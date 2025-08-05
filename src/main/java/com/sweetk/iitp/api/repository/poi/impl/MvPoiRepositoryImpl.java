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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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



    public List<MvPoi> findAll() {
        return;
    }

    public List<MvPoi> findAllWithCount(int offset, int size) {
        return;
    }


    public List<MvPoi> findByCategoryType(String categoryType) {
        StringBuilder sql = new StringBuilder("SELECT " +
                "poi_id, " +
                "language_code, " +
                "'" + categoryType + "' AS category, " +
                "search_filter_json->'search_filter'->>'" + categoryType + "' AS sub_category, " +
                "title, summary, basic_info, address_code, address_road, address_detail, latitude, longitude, detail_json, search_filter_json, " +
                "FROM mv_poi WHERE is_published = 'Y' AND is_deleted = 'N' " +
                "AND (search_filter_json->'search_filter' ? '" + categoryType + "') " +
                "ORDER BY title");

        log.debug("[MvPoi] 카테고리별 조회 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             java.sql.Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            while (rs.next()) {
                MvPoi poi = new MvPoi(
                        //rs.getLong("poi_id"),
                        rs.getString("language_code"),
                        categoryType,
                        rs.getString("sub_category"),
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
                entityList.add(poi);
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 카테고리별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return entityList;
    }



    public MvPoiPageResult findByCategoryTypeWithCount(String categoryType, int offset, int size) {
        StringBuilder sql = new StringBuilder("SELECT " +
                "poi_id, " +
                "language_code, " +
                "'" + categoryType + "' AS category, " +
                "search_filter_json->'search_filter'->>'" + categoryType + "' AS sub_category, " +
                "title, summary, basic_info, address_code, address_road, address_detail, latitude, longitude, detail_json, search_filter_json, " +
                "COUNT(*) OVER() AS total_count " +
                "FROM mv_poi WHERE is_published = 'Y' AND is_deleted = 'N' " +
                "AND (search_filter_json->'search_filter' ? '" + categoryType + "') " +
                "ORDER BY title OFFSET " + offset + " LIMIT " + size);

        log.debug("[MvPoi] 카테고리별 조회 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        long totalCount = 0L;
        try (Connection conn = dataSource.getConnection();
             java.sql.Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            int i = 0;
            while (rs.next()) {
                if (i == 0) {
                    totalCount = rs.getLong("total_count");
                }
                MvPoi poi = new MvPoi(
                        //rs.getLong("poi_id"),
                        rs.getString("language_code"),
                        categoryType,
                        rs.getString("sub_category"),
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
                entityList.add(poi);
                i++;
            }
        } catch (SQLException e) {
            log.error("[MvPoi] 카테고리별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return new MvPoiPageResult(entityList, totalCount);
    }

    @Override
    public Optional<MvPoi> findByIdWithCategory(Long poiId) {
        String sql = "SELECT " +
            "poi_id, " +
            "language_code, " +
            "title, summary, basic_info, address_code, address_road, address_detail, " +
            "latitude, longitude, detail_json, search_filter_json " +
            "FROM mv_poi WHERE poi_id = ? AND is_published = 'Y' AND is_deleted = 'N'";

        log.debug("[MvPoi] ID별 조회 쿼리: {}", sql);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, poiId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // JSON에서 카테고리 정보 추출
                    String searchFilterJson = rs.getString("search_filter_json");
                    String category = ""; // 기본값
                    String subCategory = ""; // 기본값
                    
                                         if (searchFilterJson != null && !searchFilterJson.trim().isEmpty()) {
                         try {
                             // JSON 파싱하여 첫 번째 카테고리와 서브카테고리 추출
                             com.fasterxml.jackson.databind.JsonNode jsonNode = objectMapper.readTree(searchFilterJson);
                             com.fasterxml.jackson.databind.JsonNode searchFilter = jsonNode.get("search_filter");
                             
                             if (searchFilter != null && searchFilter.isObject()) {
                                 java.util.Iterator<String> categoryNames = searchFilter.fieldNames();
                                 if (categoryNames.hasNext()) {
                                     category = categoryNames.next();
                                     com.fasterxml.jackson.databind.JsonNode subCategories = searchFilter.get(category);
                                     if (subCategories != null) {
                                         // 서브카테고리는 문자열 그대로 사용
                                         subCategory = subCategories.asText();
                                     }
                                 }
                             }
                         } catch (Exception e) {
                             log.warn("[MvPoi] JSON 파싱 실패, 기본값 사용: {}", e.getMessage());
                         }
                     }
                    
                    MvPoi poi = new MvPoi(
                        rs.getString("language_code"),
                        category,
                        subCategory,
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
                    return Optional.of(poi);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("[MvPoi] ID별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
    }

    public MvPoiPageResult findByCategoryAndSubCateWithCount(
            String category, String subCate, String name, int offset, int size
    ) {
        StringBuilder sql = new StringBuilder("SELECT " +
            "poi_id, " +
            "language_code, " +
            "'" + (category != null ? category : "") + "' AS category, " +
            "search_filter_json->'search_filter'->>'" + (category != null ? category : "") + "' AS sub_category, " +
            "title, summary, basic_info, address_code, address_road, address_detail, latitude, longitude, detail_json, search_filter_json, " +
            "COUNT(*) OVER() AS total_count " +
            "FROM mv_poi WHERE is_published = 'Y' AND is_deleted = 'N' ");
        boolean hasCategory = category != null && !category.isEmpty();
        boolean hasSubCate = subCate != null && !subCate.isEmpty();
        boolean hasName = name != null && !name.isEmpty();

        if (hasCategory) {
            sql.append("AND (search_filter_json->'search_filter' ? '").append(category).append("') ");
            if (hasSubCate) {
                if (subCate.contains(",")) {
                    String[] subCates = subCate.split(",");
                    sql.append("AND (");
                    for (int i = 0; i < subCates.length; i++) {
                        if (i > 0) sql.append(" OR ");
                        sql.append("search_filter_json->'search_filter'->>'").append(category).append("' = '").append(subCates[i].trim().replace("'", "''")).append("' ");
                    }
                    sql.append(") ");
                } else {
                    sql.append("AND search_filter_json->'search_filter'->>'").append(category).append("' LIKE '%").append(subCate.replace("'", "''")).append("%' ");
                }
            }
        }
        if (hasName) {
            sql.append("AND title LIKE '%").append(name.replace("'", "''")).append("%' ");
        }
        sql.append("ORDER BY title OFFSET ").append(offset).append(" LIMIT ").append(size);

        log.debug("[MvPoi] 최종 실행 쿼리: {}", sql);

        List<MvPoi> entityList = new ArrayList<>();
        long totalCount = 0L;
        try (Connection conn = dataSource.getConnection();
             java.sql.Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            int i = 0;
            while (rs.next()) {
                if (i == 0) {
                    totalCount = rs.getLong("total_count");
                }
                MvPoi poi = new MvPoi(
                    //rs.getLong("poi_id"),
                    rs.getString("language_code"),
                    category,
                    rs.getString("sub_category"),
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
                entityList.add(poi);
                i++;
            }
            log.debug("[MvPoi] JDBC 쿼리 실행 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[MvPoi] JDBC 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        return new MvPoiPageResult(entityList, totalCount);
    }

    public MvPoiPageResult findByLocationWithCount(
            BigDecimal latitude, BigDecimal longitude, BigDecimal radius, int offset, int size
    ) {
        String sql = "SELECT *, COUNT(*) OVER() AS total_count FROM mv_poi " +
                "WHERE is_published = 'Y' AND is_deleted = 'N' " +
                "AND ST_DWithin(" +
            "ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, " +
                "ST_SetSRID(ST_MakePoint(?, ?), 4326)::geography, ?) " +
                "ORDER BY title OFFSET ? LIMIT ?";
        
        List<MvPoi> entityList = new ArrayList<>();
        long totalCount = 0;
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            ps.setBigDecimal(3, radius.multiply(new java.math.BigDecimal(1000)));
            ps.setInt(4, offset);
            ps.setInt(5, size);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                    
                    entityList.add(new MvPoi(
                        //rs.getLong("poi_id"),
                        rs.getString("language_code"),
                        null,
                        null,
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
                    ));
                }
            }
            
        } catch (SQLException e) {
            log.error("[MvPoi] JDBC 위치 기반 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return new MvPoiPageResult(entityList, totalCount);
    }
} 
