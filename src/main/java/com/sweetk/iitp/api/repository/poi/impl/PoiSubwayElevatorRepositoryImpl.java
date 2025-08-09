package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.config.DistanceCalculationConfig;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevator;
import com.sweetk.iitp.api.dto.poi.PoiSubwayElevatorLocation;
import com.sweetk.iitp.api.util.RepositoryUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Repository
public class PoiSubwayElevatorRepositoryImpl implements PoiSubwayElevatorRepositoryCustom {
    private final DistanceCalculationConfig distanceConfig;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    // 공통 컬럼 정의
    private static final String ELEVATOR_COMMON_COLUMNS = 
            "subway_id, sido_code, node_link_type, node_wkt, node_id, node_type_code, node_type_name, " +
            "sigungu_code, sigungu_name, eupmyeondong_code, eupmyeondong_name, station_code, station_name, " +
            "latitude, longitude, base_dt";

    private static final String ELEVATOR_TOT_CNT_COLUMN =  " COUNT(*) OVER() AS total_count ";
    private static final String ELEVATOR_BASE_FROM = " FROM poi_subway_elevator WHERE del_yn = 'N' ";

    private static final String ELEVATOR_ORDER_BY = " ORDER BY station_name ";
    private static final String ELEVATOR_ORDER_BY_DISTANCE = " ORDER BY distance";


    // SQL 상수 정의
    private static final String ELEVATOR_QUERY = "SELECT " + ELEVATOR_COMMON_COLUMNS + " " +
                                                        ELEVATOR_BASE_FROM;

    private static final String ELEVATOR_QUERY_WITH_COUNT = "SELECT " + ELEVATOR_COMMON_COLUMNS + ", " +
                                                                    ELEVATOR_TOT_CNT_COLUMN +
                                                                    ELEVATOR_BASE_FROM;


    // 위치 검색용 기본 쿼리 (거리 계산 포함)
    private final String ELEVATOR_LOCATION_QUERY;
    private final String ELEVATOR_LOCATION_QUERY_WITH_COUNT;

    public PoiSubwayElevatorRepositoryImpl(DistanceCalculationConfig distanceConfig) {
        this.distanceConfig = distanceConfig;
        
        // 기본 위치 검색 쿼리 생성
        this.ELEVATOR_LOCATION_QUERY = "SELECT " + ELEVATOR_COMMON_COLUMNS + ", " +
                distanceConfig.getDistanceCalculationSql() + " AS distance " +
                ELEVATOR_BASE_FROM;

        this.ELEVATOR_LOCATION_QUERY_WITH_COUNT = "SELECT " + ELEVATOR_COMMON_COLUMNS + ", " +
                distanceConfig.getDistanceCalculationSql() + " AS distance, " +
                ELEVATOR_TOT_CNT_COLUMN +
                ELEVATOR_BASE_FROM;
    }



    /*******************************
     ** 지하철 엘리베이터 ID로 조회
     *******************************/
    @Override
    public Optional<PoiSubwayElevator> findByIdToDto(Integer subwayId) {
        String sql = ELEVATOR_QUERY + "AND subway_id = ? ";

        log.debug("[PoiSubwayElevator] ID별 조회 쿼리: {}", sql);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, subwayId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                    return Optional.of(elevator);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] ID별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
    }



    /*******************************
     ** 전체 지하철 엘리베이터 조회
     *******************************/
    @Override
    public List<PoiSubwayElevator> findAllToDto() {
        String sql = ELEVATOR_QUERY + ELEVATOR_ORDER_BY;

        log.debug("[PoiSubwayElevator] 전체 조회 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                    entityList.add(elevator);
                }
            }
            log.debug("[PoiSubwayElevator] 전체 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 전체 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }

        return entityList;
    }

    @Override
    public PoiPageResult<PoiSubwayElevator> findAllWithPagingCount(int offset, int size) {
        StringBuilder sql = new StringBuilder(ELEVATOR_QUERY_WITH_COUNT).append(ELEVATOR_ORDER_BY);
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);

        log.debug("[PoiSubwayElevator] 전체 조회 쿼리 (페이징): {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                    entityList.add(elevator);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiSubwayElevator] 전체 조회 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 전체 조회 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database query failed", e);
        }

        return new PoiPageResult<>(entityList, totalCount);
    }






    @Override
    public List<PoiSubwayElevator> findBySidoCodeToDto(String sidoCode) {
        String sql = ELEVATOR_QUERY + "AND sido_code = ? " + ELEVATOR_ORDER_BY;

        log.debug("[PoiSubwayElevator] 시도별 조회 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sidoCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                    entityList.add(elevator);
                }
            }
            log.debug("[PoiSubwayElevator] 시도별 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 시도별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    @Override
    public List<PoiSubwayElevator> findByCategoryConditions(String stationName, String sidoCode, 
                                                          Integer nodeTypeCode) {
        StringBuilder sql = new StringBuilder(ELEVATOR_QUERY);
        sql.append(buildCategoryConditionsSql(stationName, sidoCode, nodeTypeCode));
        sql.append(ELEVATOR_ORDER_BY);

        log.debug("[PoiSubwayElevator] 카테고리 조건 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (sidoCode != null && !sidoCode.trim().isEmpty()) {
                ps.setString(paramIndex++, sidoCode);
            }
            if (nodeTypeCode != null) {
                ps.setInt(paramIndex++, nodeTypeCode);
            }
            if (stationName != null && !stationName.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + stationName + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                    entityList.add(elevator);
                }
            }
            log.debug("[PoiSubwayElevator] 카테고리 조건 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 카테고리 조건 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    @Override
    public PoiPageResult<PoiSubwayElevator> findByCategoryConditionsWithPagingCount(
            String stationName, String sidoCode, Integer nodeTypeCode, int offset, int size) {
        StringBuilder sql = new StringBuilder(ELEVATOR_QUERY_WITH_COUNT);
        sql.append(buildCategoryConditionsSql(stationName, sidoCode, nodeTypeCode));
        sql.append(ELEVATOR_ORDER_BY);
        sql = new StringBuilder(addPagingToQuery(sql.toString(), offset, size));

        log.debug("[PoiSubwayElevator] 카테고리 조건 검색 쿼리 (페이징): {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (sidoCode != null && !sidoCode.trim().isEmpty()) {
                ps.setString(paramIndex++, sidoCode);
            }
            if (nodeTypeCode != null) {
                ps.setInt(paramIndex++, nodeTypeCode);
            }
            if (stationName != null && !stationName.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + stationName + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                    entityList.add(elevator);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiSubwayElevator] 카테고리 조건 검색 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 카테고리 조건 검색 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return new PoiPageResult<>(entityList, totalCount);
    }

    @Override
    public List<PoiSubwayElevator> findBySigunguConditions(String sidoCode, String sigunguCode) {
        String sql = ELEVATOR_QUERY + "AND sido_code = ? AND sigungu_code = ? " + ELEVATOR_ORDER_BY;

        log.debug("[PoiSubwayElevator] 시군구별 조회 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sidoCode);
            ps.setString(2, sigunguCode);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                    entityList.add(elevator);
                }
            }
            log.debug("[PoiSubwayElevator] 시군구별 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 시군구별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    @Override
    public PoiPageResult<PoiSubwayElevator> findBySigunguConditionsWithPagingCount(String sidoCode, String sigunguCode,
                                                                                  int offset, int size) {
        String sql = ELEVATOR_QUERY_WITH_COUNT + "AND sido_code = ? AND sigungu_code = ? " +
                    ELEVATOR_ORDER_BY + " OFFSET " + offset + " LIMIT " + size;

        log.debug("[PoiSubwayElevator] 시군구별 조회 쿼리 (페이징): {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sidoCode);
            ps.setString(2, sigunguCode);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                    entityList.add(elevator);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiSubwayElevator] 시군구별 조회 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 시군구별 조회 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return new PoiPageResult<>(entityList, totalCount);
    }

    @Override
    public List<PoiSubwayElevator> findByLocation(BigDecimal latitude, BigDecimal longitude, 
                                                 BigDecimal radius) {
        String sql = ELEVATOR_QUERY +
                    "AND ST_DWithin(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), " +
                    "ST_SetSRID(ST_MakePoint(?, ?), 4326), ?) " +
                    ELEVATOR_ORDER_BY;

        log.debug("[PoiSubwayElevator] 위치 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            ps.setBigDecimal(3, radius);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                    entityList.add(elevator);
                }
                log.debug("[PoiSubwayElevator] 위치 검색 완료 - 결과 개수: {}", entityList.size());
            }
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 위치 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    @Override
    public PoiPageResult<PoiSubwayElevator> findByLocationWithPagingCount(BigDecimal latitude, BigDecimal longitude,
                                                                         BigDecimal radius, int offset, int size) {
        String sql = ELEVATOR_QUERY_WITH_COUNT +
                    "AND ST_DWithin(ST_SetSRID(ST_MakePoint(longitude, latitude), 4326), " +
                    "ST_SetSRID(ST_MakePoint(?, ?), 4326), ?) " +
                    ELEVATOR_ORDER_BY + " OFFSET " + offset + " LIMIT " + size;

        log.debug("[PoiSubwayElevator] 위치 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            ps.setBigDecimal(3, radius);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                    entityList.add(elevator);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
                log.debug("[PoiSubwayElevator] 위치 검색 완료 - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
            }
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 위치 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return new PoiPageResult<>(entityList, totalCount);
    }

    @Override
    public PoiPageResult<PoiSubwayElevator> findBySidoCodeWithPagingCount(String sidoCode, int offset, int size) {
        String sql = ELEVATOR_QUERY_WITH_COUNT + "AND sido_code = ? " +
                    ELEVATOR_ORDER_BY + " OFFSET " + offset + " LIMIT " + size;

        log.debug("[PoiSubwayElevator] 시도 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sidoCode);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = setPoiSubwayElevator(rs);
                    entityList.add(elevator);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
                log.debug("[PoiSubwayElevator] 시도 검색 완료 - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
            }
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 시도 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return new PoiPageResult<>(entityList, totalCount);
    }



    /*******************************
     **  거리 정보 포함 위치 기반 검색 지하철 엘리베이터 조회
     *******************************/

    @Override
    public List<PoiSubwayElevatorLocation> findByLocationWithDistance(BigDecimal latitude, BigDecimal longitude, 
                                                                     BigDecimal radius) {
        StringBuilder sql = new StringBuilder(ELEVATOR_LOCATION_QUERY);
        
        // 거리 필터링 조건 추가
        sql.append(distanceConfig.getDistanceFilterSql(latitude, longitude, radius.multiply(new BigDecimal(1000))));
        sql.append(ELEVATOR_ORDER_BY_DISTANCE);

        log.debug("[PoiSubwayElevator] 거리 정보 포함 위치 기반 검색 쿼리: {}", sql);
        
        List<PoiSubwayElevatorLocation> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            // 거리 계산을 위한 파라미터 설정 (longitude, latitude)
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevatorLocation elevator = setPoiSubwayElevatorLocation(rs);
                    entityList.add(elevator);
                }
            }
            log.debug("[PoiSubwayElevator] 거리 정보 포함 위치 기반 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 거리 정보 포함 위치 기반 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database location with distance query failed", e);
        }
        
        return entityList;
    }

    @Override
    public PoiPageResult<PoiSubwayElevatorLocation> findByLocationWithDistanceAndPagingCount(BigDecimal latitude, BigDecimal longitude,
                                                                                             BigDecimal radius, int offset, int size) {
        StringBuilder sql = new StringBuilder(ELEVATOR_LOCATION_QUERY_WITH_COUNT);
        
        // 거리 필터링 조건 추가
        sql.append(distanceConfig.getDistanceFilterSql(latitude, longitude, radius.multiply(new BigDecimal(1000))));
        sql.append(ELEVATOR_ORDER_BY_DISTANCE);
        
        // 페이징 추가
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);

        log.debug("[PoiSubwayElevator] 거리 정보 포함 위치 기반 검색 쿼리 (페이징): {}", sql);

        List<PoiSubwayElevatorLocation> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            // 거리 계산을 위한 파라미터 설정 (longitude, latitude)
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevatorLocation elevator = setPoiSubwayElevatorLocation(rs);
                    entityList.add(elevator);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiSubwayElevator] 거리 정보 포함 위치 기반 검색 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 거리 정보 포함 위치 기반 검색 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database location with distance and paging query failed", e);
        }
        
        return new PoiPageResult<>(entityList, totalCount);
    }

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
     * ResultSet에서 PoiSubwayElevatorLocation 객체 생성
     */
    private PoiSubwayElevatorLocation setPoiSubwayElevatorLocation(ResultSet rs) throws SQLException {
        // 직접 Location 객체 생성 (메모리 효율성)
        return new PoiSubwayElevatorLocation(
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
                rs.getObject("latitude", Double.class),
                rs.getObject("longitude", Double.class),
                rs.getString("base_dt"),
                rs.getInt("distance")
        );
    }

    /**
     * 카테고리 조건 SQL 생성 (PreparedStatement용)
     */
    private String buildCategoryConditionsSql(String stationName, String sidoCode, Integer nodeTypeCode) {
        StringBuilder sql = new StringBuilder();

        // 인덱스 활용을 위한 조건 순서 최적화
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            sql.append("AND sido_code = ? ");
        }
        if (nodeTypeCode != null) {
            sql.append("AND node_type_code = ? ");
        }
        if (stationName != null && !stationName.trim().isEmpty()) {
            sql.append("AND station_name LIKE ? ");
        }

        return sql.toString();
    }
} 