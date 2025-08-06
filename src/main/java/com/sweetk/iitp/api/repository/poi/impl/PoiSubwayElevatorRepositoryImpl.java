package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.poi.PoiSubwayElevator;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PoiSubwayElevatorRepositoryImpl implements PoiSubwayElevatorRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(PoiSubwayElevatorRepositoryImpl.class);

    // SQL 상수 정의
    private static final String ELEVATOR_BASE_QUERY = 
        "SELECT subway_id, sido_code, node_link_type, node_wkt, node_id, node_type_code, node_type_name, " +
        "sigungu_code, sigungu_name, eupmyeondong_code, eupmyeondong_name, station_code, station_name, " +
        "latitude, longitude, base_dt " +
        "FROM poi_subway_elevator WHERE del_yn = 'N' ";

    private static final String ELEVATOR_BASE_QUERY_WITH_COUNT = 
        "SELECT subway_id, sido_code, node_link_type, node_wkt, node_id, node_type_code, node_type_name, " +
        "sigungu_code, sigungu_name, eupmyeondong_code, eupmyeondong_name, station_code, station_name, " +
        "latitude, longitude, base_dt, " +
        "COUNT(*) OVER() AS total_count " +
        "FROM poi_subway_elevator WHERE del_yn = 'N' ";

    private static final String ELEVATOR_ORDER_BY = "ORDER BY station_name ";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    /**
     * ResultSet에서 PoiSubwayElevator 객체 생성
     */
    private PoiSubwayElevator setPoiSubwayElevator(ResultSet rs) throws SQLException {
        return new PoiSubwayElevator(
            rs.getInt("subway_id"),
            rs.getString("sido_code"),
            rs.getString("node_link_type"),
            rs.getString("node_wkt"),
            rs.getLong("node_id"),
            rs.getInt("node_type_code"),
            rs.getString("node_type_name"),
            rs.getString("sigungu_code"),
            rs.getString("sigungu_name"),
            rs.getString("eupmyeondong_code"),
            rs.getString("eupmyeondong_name"),
            rs.getString("station_code"),
            rs.getString("station_name"),
            rs.getBigDecimal("latitude") != null ? rs.getBigDecimal("latitude").doubleValue() : null,
            rs.getBigDecimal("longitude") != null ? rs.getBigDecimal("longitude").doubleValue() : null,
            rs.getString("base_dt")
        );
    }

    /**
     * 카테고리 조건 SQL 생성
     */
    private String buildCategoryConditionsSql(String stationName, String sidoCode, Integer nodeTypeCode) {
        StringBuilder sql = new StringBuilder();
        
        // 인덱스 활용을 위한 조건 순서 최적화
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            sql.append("AND sido_code = '").append(sidoCode.replace("'", "''")).append("' ");
        }
        if (nodeTypeCode != null) {
            sql.append("AND node_type_code = ").append(nodeTypeCode).append(" ");
        }
        if (stationName != null && !stationName.trim().isEmpty()) {
            sql.append("AND station_name LIKE '%").append(stationName.replace("'", "''")).append("%' ");
        }
        
        return sql.toString();
    }

    // Helper method to add paging to query
    private String addPagingToQuery(String baseQuery, int offset, int size) {
        return baseQuery + " OFFSET " + offset + " LIMIT " + size;
    }

    @Override
    public java.util.Optional<PoiSubwayElevator> findByIdToDto(Integer subwayId) {
        StringBuilder sql = new StringBuilder(ELEVATOR_BASE_QUERY);
        sql.append("AND subway_id = ").append(subwayId).append(" ");
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString());
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return java.util.Optional.of(setPoiSubwayElevator(rs));
            } else {
                return java.util.Optional.empty();
            }
        } catch (SQLException e) {
            log.error("지하철 엘리베이터 ID 조회 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("지하철 엘리베이터 조회 실패", e);
        }
    }

    @Override
    public List<PoiSubwayElevator> findBySidoCodeToDto(String sidoCode) {
        StringBuilder sql = new StringBuilder(ELEVATOR_BASE_QUERY);
        sql.append("AND sido_code = '").append(sidoCode.replace("'", "''")).append("' ");
        sql.append(ELEVATOR_ORDER_BY);

        log.debug("[PoiSubwayElevator] 시도별 조회 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                entityList.add(elevator);
            }
            log.debug("[PoiSubwayElevator] 시도별 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 시도별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database sido query failed", e);
        }
        
        return entityList;
    }

    @Override
    public List<PoiSubwayElevator> findByCategoryConditions(String stationName, String sidoCode, 
                                                           Integer nodeTypeCode) {
        StringBuilder sql = new StringBuilder(ELEVATOR_BASE_QUERY);
        sql.append(buildCategoryConditionsSql(stationName, sidoCode, nodeTypeCode));
        sql.append(ELEVATOR_ORDER_BY);

        log.debug("[PoiSubwayElevator] 카테고리 검색 쿼리 (전체): {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                entityList.add(elevator);
            }
            log.debug("[PoiSubwayElevator] 카테고리 검색 완료 (전체) - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 카테고리 검색 쿼리 실행 중 오류 발생 (전체)", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiSubwayElevator> findByCategoryConditionsWithPaging(
            String stationName, String sidoCode, Integer nodeTypeCode, int offset, int size) {
        StringBuilder sql = new StringBuilder(ELEVATOR_BASE_QUERY_WITH_COUNT);
        sql.append(buildCategoryConditionsSql(stationName, sidoCode, nodeTypeCode));
        sql.append(ELEVATOR_ORDER_BY);
        sql = new StringBuilder(addPagingToQuery(sql.toString(), offset, size));

        log.debug("[PoiSubwayElevator] 카테고리 검색 쿼리 (페이징 + 카운트): {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                entityList.add(elevator);
                // 첫 번째 행에서 total_count 가져오기
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
            }
            log.debug("[PoiSubwayElevator] 카테고리 검색 완료 (페이징 + 카운트) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 카테고리 검색 쿼리 실행 중 오류 발생 (페이징 + 카운트)", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }



    @Override
    public List<PoiSubwayElevator> findBySigunguConditions(String sidoCode, String sigunguCode) {
        StringBuilder sql = new StringBuilder(ELEVATOR_BASE_QUERY);
        sql.append("AND sido_code = '").append(sidoCode.replace("'", "''")).append("' ");
        sql.append("AND sigungu_code = '").append(sigunguCode.replace("'", "''")).append("' ");
        sql.append(ELEVATOR_ORDER_BY);

        log.debug("[PoiSubwayElevator] 시군구 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                entityList.add(elevator);
            }
            log.debug("[PoiSubwayElevator] 시군구 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 시군구 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiSubwayElevator> findBySigunguConditionsWithPaging(String sidoCode, String sigunguCode, 
                                                                     int offset, int size) {
        StringBuilder sql = new StringBuilder(ELEVATOR_BASE_QUERY_WITH_COUNT);
        sql.append("AND sido_code = '").append(sidoCode.replace("'", "''")).append("' ");
        sql.append("AND sigungu_code = '").append(sigunguCode.replace("'", "''")).append("' ");
        sql.append(ELEVATOR_ORDER_BY);
        sql = new StringBuilder(addPagingToQuery(sql.toString(), offset, size));

        log.debug("[PoiSubwayElevator] 시군구 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                entityList.add(elevator);
                // 첫 번째 행에서 total_count 가져오기
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
            }
            log.debug("[PoiSubwayElevator] 시군구 검색 완료 - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 시군구 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }



    @Override
    public List<PoiSubwayElevator> findByLocation(BigDecimal latitude, BigDecimal longitude, 
                                                  BigDecimal radius) {
        StringBuilder sql = new StringBuilder(ELEVATOR_BASE_QUERY);
        sql.append("AND ST_DWithin(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), ");
        sql.append("ST_SetSRID(ST_MakePoint(").append(longitude).append(", ").append(latitude).append("), 4326), ");
        sql.append(radius).append(") ");
        sql.append(ELEVATOR_ORDER_BY);

        log.debug("[PoiSubwayElevator] 위치 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                entityList.add(elevator);
            }
            log.debug("[PoiSubwayElevator] 위치 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 위치 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiSubwayElevator> findByLocationWithPaging(BigDecimal latitude, BigDecimal longitude, 
                                                            BigDecimal radius, int offset, int size) {
        StringBuilder sql = new StringBuilder(ELEVATOR_BASE_QUERY_WITH_COUNT);
        sql.append("AND ST_DWithin(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), ");
        sql.append("ST_SetSRID(ST_MakePoint(").append(longitude).append(", ").append(latitude).append("), 4326), ");
        sql.append(radius).append(") ");
        sql.append(ELEVATOR_ORDER_BY);
        sql = new StringBuilder(addPagingToQuery(sql.toString(), offset, size));

        log.debug("[PoiSubwayElevator] 위치 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                entityList.add(elevator);
                // 첫 번째 행에서 total_count 가져오기
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
            }
            log.debug("[PoiSubwayElevator] 위치 검색 완료 - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 위치 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }



    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiSubwayElevator> findBySidoCodeWithPaging(String sidoCode, int offset, int size) {
        StringBuilder sql = new StringBuilder(ELEVATOR_BASE_QUERY_WITH_COUNT);
        sql.append("AND sido_code = '").append(sidoCode.replace("'", "''")).append("' ");
        sql.append(ELEVATOR_ORDER_BY);
        sql = new StringBuilder(addPagingToQuery(sql.toString(), offset, size));

        log.debug("[PoiSubwayElevator] 시도 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                entityList.add(elevator);
                // 첫 번째 행에서 total_count 가져오기
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
            }
            log.debug("[PoiSubwayElevator] 시도 검색 완료 - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 시도 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }



    @Override
    public List<PoiSubwayElevator> findAllToDto() {
        StringBuilder sql = new StringBuilder(ELEVATOR_BASE_QUERY);
        sql.append(ELEVATOR_ORDER_BY);

        log.debug("[PoiSubwayElevator] 전체 조회 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                entityList.add(elevator);
            }
            log.debug("[PoiSubwayElevator] 전체 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 전체 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiSubwayElevator> findAllWithPaging(int offset, int size) {
        StringBuilder sql = new StringBuilder(ELEVATOR_BASE_QUERY_WITH_COUNT);
        sql.append(ELEVATOR_ORDER_BY);
        sql = new StringBuilder(addPagingToQuery(sql.toString(), offset, size));

        log.debug("[PoiSubwayElevator] 전체 조회 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                entityList.add(elevator);
                // 첫 번째 행에서 total_count 가져오기
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
            }
            log.debug("[PoiSubwayElevator] 전체 조회 완료 - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 전체 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }


} 