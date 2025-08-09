package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.config.DistanceCalculationConfig;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilityLocation;
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
public class PoiTourBfFacilityRepositoryImpl implements PoiTourBfFacilityRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(PoiTourBfFacilityRepositoryImpl.class);

    private final DistanceCalculationConfig distanceConfig;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    // 공통 컬럼 정의
    private static final String FACILITY_COMMON_COLUMNS = 
            "fclt_id, sido_code, fclt_name, toilet_yn, elevator_yn, parking_yn, slope_yn, " +
            "subway_yn, bus_stop_yn, wheelchair_rent_yn, tactile_map_yn, audio_guide_yn, " +
            "nursing_room_yn, accessible_room_yn, stroller_rent_yn, addr_road, addr_jibun, " +
            "latitude, longitude, base_dt";

    private static final String FACILITY_TOT_CNT_COLUMN =  "COUNT(*) OVER() AS total_count ";
    private static final String FACILITY_BASE_FROM = " FROM poi_tour_bf_facility WHERE del_yn = 'N' ";

    private static final String FACILITY_ORDER_BY = " ORDER BY fclt_name ";
    private static final String FACILITY_ORDER_BY_DISTANCE = " ORDER BY distance ";

    // Common SQL constants
    private static final String FACILITY_QUERY = "SELECT " + FACILITY_COMMON_COLUMNS + " " +
                                                FACILITY_BASE_FROM;

    private static final String FACILITY_QUERY_WITH_COUNT = "SELECT " + FACILITY_COMMON_COLUMNS + ", " +
                                                            FACILITY_TOT_CNT_COLUMN +
                                                            FACILITY_BASE_FROM;

    // 위치 검색용 기본 쿼리 (거리 계산 포함)
    private final String FACILITY_LOCATION_QUERY;
    private final String FACILITY_LOCATION_QUERY_WITH_COUNT;



    public PoiTourBfFacilityRepositoryImpl(DistanceCalculationConfig distanceConfig) {
        this.distanceConfig = distanceConfig;
        
        // 기본 위치 검색 쿼리 생성
        this.FACILITY_LOCATION_QUERY = "SELECT " + FACILITY_COMMON_COLUMNS + ", " +
                                        distanceConfig.getDistanceCalculationSql() + " AS distance " +
                                        FACILITY_BASE_FROM;

        this.FACILITY_LOCATION_QUERY_WITH_COUNT = "SELECT " + FACILITY_COMMON_COLUMNS + ", " +
                                                    distanceConfig.getDistanceCalculationSql() + " AS distance, " +
                                                    FACILITY_TOT_CNT_COLUMN  +
                                                    FACILITY_BASE_FROM;
    }




    /*******************************
     ** 무장애 관광지 시설 ID로 조회
     *******************************/
    @Override
    public Optional<PoiTourBfFacility> findByIdToDto(Integer fcltId) {
        String sql = FACILITY_QUERY + "AND fclt_id = ? ";

        log.debug("[PoiTourBfFacility] ID별 조회 쿼리: {}", sql);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, fcltId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                    return Optional.of(facility);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] ID별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
    }




    /*******************************
     ** 전체 무장애 관광지 시설 조회
     *******************************/
    // 전체 무장애 관광지 시설 조회 (전체 결과)
    @Override
    public List<PoiTourBfFacility> findAllToDto() {
        String sql = FACILITY_QUERY + FACILITY_ORDER_BY;

        log.debug("[PoiTourBfFacility] 전체 조회 쿼리: {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                    entityList.add(facility);
                }
            }
            log.debug("[PoiTourBfFacility] 전체 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 전체 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }

        return entityList;
    }

    // 전체 무장애 관광지 시설 조회 (페이징)
    @Override
    public PoiPageResult<PoiTourBfFacility> findAllWithPagingCount(int offset, int size) {
        StringBuilder sql = new StringBuilder(FACILITY_QUERY_WITH_COUNT).append(FACILITY_ORDER_BY);
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);

        log.debug("[PoiTourBfFacility] 전체 조회 쿼리 (페이징): {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                    entityList.add(facility);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiTourBfFacility] 전체 조회 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 전체 조회 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database query failed", e);
        }

        return new PoiPageResult<>(entityList, totalCount);
    }



    /*******************************
     ** 시도별 무장애 관광지 시설 조회
     *******************************/
    // 시도 코드로 무장애 관광지 시설 조회 (DTO 반환)
    @Override
    public List<PoiTourBfFacility> findBySidoCode(String sidoCode) {
        StringBuilder sql = new StringBuilder(FACILITY_QUERY);
        sql.append("AND sido_code = ? ");
        sql.append(FACILITY_ORDER_BY);

        log.debug("[PoiTourBfFacility] 시도별 조회 쿼리: {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setString(1, sidoCode);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                    entityList.add(facility);
                }
            }
            log.debug("[PoiTourBfFacility] 시도별 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 시도별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    // 시도 코드로 무장애 관광지 시설 조회 (페이징)
    @Override
    public PoiPageResult<PoiTourBfFacility> findBySidoCodeWithPagingCount(String sidoCode, int offset, int size) {

        StringBuilder sql = new StringBuilder(FACILITY_QUERY_WITH_COUNT);
        sql.append("AND sido_code = ? ");
        sql.append(FACILITY_ORDER_BY);
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);
        log.debug("[PoiTourBfFacility] 시도별 조회 쿼리 (페이징): {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setString(1, sidoCode);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                    entityList.add(facility);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiTourBfFacility] 시도별 조회 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 시도별 조회 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database query failed", e);
        }

        return new PoiPageResult<>(entityList, totalCount);
    }




    /*******************************
     ** 카테고리 기반 무장애 관광지 시설 조회
     *******************************/
    // 카테고리 조건으로 무장애 관광지 시설 검색 (전체 결과)
    @Override
    public List<PoiTourBfFacility> findByCategoryConditions(String fcltName, String sidoCode, 
                                                          String toiletYn, String elevatorYn, 
                                                          String parkingYn, String wheelchairRentYn, 
                                                          String tactileMapYn, String audioGuideYn) {
        StringBuilder sql = new StringBuilder(FACILITY_QUERY);
        sql = buildCategoryConditionsSql(sql, fcltName, sidoCode, toiletYn, elevatorYn, parkingYn,
                                       wheelchairRentYn, tactileMapYn, audioGuideYn);
        sql.append(FACILITY_ORDER_BY);

        log.debug("[PoiTourBfFacility] 카테고리 조건 검색 쿼리: {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                    entityList.add(facility);
                }
            }
            log.debug("[PoiTourBfFacility] 카테고리 조건 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 카테고리 조건 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    // 카테고리 조건으로 무장애 관광지 시설 검색 (페이징 + 총 개수)
    @Override
    public PoiPageResult<PoiTourBfFacility> findByCategoryConditionsWithPagingCount(
            String fcltName, String sidoCode, String toiletYn, String elevatorYn, 
            String parkingYn, String wheelchairRentYn, String tactileMapYn, String audioGuideYn, 
            int offset, int size) {
        StringBuilder sql = new StringBuilder(FACILITY_QUERY_WITH_COUNT);
        sql = buildCategoryConditionsSql(sql, fcltName, sidoCode, toiletYn, elevatorYn, parkingYn,
                                       wheelchairRentYn, tactileMapYn, audioGuideYn);
        sql.append(FACILITY_ORDER_BY);
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);

        log.debug("[PoiTourBfFacility] 카테고리 조건 검색 쿼리 (페이징): {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                    entityList.add(facility);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiTourBfFacility] 카테고리 조건 검색 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 카테고리 조건 검색 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return new PoiPageResult<>(entityList, totalCount);
    }




    /*******************************
     ** 위치 기반 무장애 관광지 시설 조회
     *******************************/
    // 거리 정보 포함 위치 기반 무장애 관광지 시설 검색 (전체 결과)
    @Override
    public List<PoiTourBfFacilityLocation> findByLocationWithConditions(BigDecimal latitude, BigDecimal longitude,
                                                                     BigDecimal radius,
                                                                      String fcltName, //String sidoCode,
                                                                      String toiletYn, String elevatorYn,
                                                                      String parkingYn, String wheelchairRentYn,
                                                                      String tactileMapYn, String audioGuideYn) {
        StringBuilder sql = new StringBuilder(FACILITY_LOCATION_QUERY);
        sql = buildCategoryConditionsSql(sql, fcltName, null, toiletYn, elevatorYn, parkingYn,
                wheelchairRentYn, tactileMapYn, audioGuideYn);
        sql.append(distanceConfig.getDistanceFilterSql(latitude, longitude, radius.multiply(new BigDecimal(1000))));
        sql.append(FACILITY_ORDER_BY_DISTANCE);

        log.debug("[PoiTourBfFacility] 거리 정보 포함 위치 기반 검색 쿼리: {}", sql);
        
        List<PoiTourBfFacilityLocation> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            // 거리 계산을 위한 파라미터 설정 (longitude, latitude)
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiTourBfFacilityLocation facility = setPoiTourBfFacilityLocation(rs);
                    entityList.add(facility);
                }
            }
            log.debug("[PoiTourBfFacility] 거리 정보 포함 위치 기반 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 거리 정보 포함 위치 기반 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database location with distance query failed", e);
        }
        
        return entityList;
    }

    // 거리 정보 포함 위치 기반 무장애 관광지 시설 검색 (페이징)
    @Override
    public PoiPageResult<PoiTourBfFacilityLocation> findByLocationWithConditionsAndPagingCount(BigDecimal latitude, BigDecimal longitude,
                                                                                             BigDecimal radius,
                                                                                             String fcltName, //String sidoCode,
                                                                                             String toiletYn, String elevatorYn,
                                                                                             String parkingYn, String wheelchairRentYn,
                                                                                             String tactileMapYn, String audioGuideYn,
                                                                                             int offset, int size) {
        StringBuilder sql = new StringBuilder(FACILITY_LOCATION_QUERY_WITH_COUNT);
        sql = buildCategoryConditionsSql(sql, fcltName, null, toiletYn, elevatorYn, parkingYn,
                wheelchairRentYn, tactileMapYn, audioGuideYn);
        sql.append(distanceConfig.getDistanceFilterSql(latitude, longitude, radius.multiply(new BigDecimal(1000))));
        sql.append(FACILITY_ORDER_BY_DISTANCE);
        
        // 페이징 추가
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);

        log.debug("[PoiTourBfFacility] 거리 정보 포함 위치 기반 검색 쿼리 (페이징): {}", sql);

        List<PoiTourBfFacilityLocation> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            // 거리 계산을 위한 파라미터 설정 (longitude, latitude)
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiTourBfFacilityLocation facility = setPoiTourBfFacilityLocation(rs);
                    entityList.add(facility);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiTourBfFacility] 거리 정보 포함 위치 기반 검색 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 거리 정보 포함 위치 기반 검색 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database location with distance and paging query failed", e);
        }
        
        return new PoiPageResult<>(entityList, totalCount);
    }



    // Helper method to create PoiTourBfFacility from ResultSet
    private PoiTourBfFacility setPoiTourBfFacility(ResultSet rs) throws SQLException {
        return new PoiTourBfFacility(
                rs.getInt("fclt_id"),
                rs.getString("sido_code"),
                rs.getString("fclt_name"),
                rs.getString("toilet_yn"),
                rs.getString("elevator_yn"),
                rs.getString("parking_yn"),
                rs.getString("slope_yn"),
                rs.getString("subway_yn"),
                rs.getString("bus_stop_yn"),
                rs.getString("wheelchair_rent_yn"),
                rs.getString("tactile_map_yn"),
                rs.getString("audio_guide_yn"),
                rs.getString("nursing_room_yn"),
                rs.getString("accessible_room_yn"),
                rs.getString("stroller_rent_yn"),
                rs.getString("addr_road"),
                rs.getString("addr_jibun"),
                rs.getBigDecimal("latitude") != null ? rs.getBigDecimal("latitude").doubleValue() : null,
                rs.getBigDecimal("longitude") != null ? rs.getBigDecimal("longitude").doubleValue() : null,
                rs.getDate("base_dt") != null ? rs.getDate("base_dt").toLocalDate() : null
        );
    }

    // Helper method to create PoiTourBfFacilityLocation from ResultSet
    private PoiTourBfFacilityLocation setPoiTourBfFacilityLocation(ResultSet rs) throws SQLException {
        // 직접 Location 객체 생성 (메모리 효율성)
        return new PoiTourBfFacilityLocation(
                rs.getInt("fclt_id"),
                rs.getString("sido_code"),
                rs.getString("fclt_name"),
                rs.getString("toilet_yn"),
                rs.getString("elevator_yn"),
                rs.getString("parking_yn"),
                rs.getString("slope_yn"),
                rs.getString("subway_yn"),
                rs.getString("bus_stop_yn"),
                rs.getString("wheelchair_rent_yn"),
                rs.getString("tactile_map_yn"),
                rs.getString("audio_guide_yn"),
                rs.getString("nursing_room_yn"),
                rs.getString("accessible_room_yn"),
                rs.getString("stroller_rent_yn"),
                rs.getString("addr_road"),
                rs.getString("addr_jibun"),
                rs.getObject("latitude", Double.class),
                rs.getObject("longitude", Double.class),
                rs.getDate("base_dt") != null ? rs.getDate("base_dt").toLocalDate() : null,
                rs.getInt("distance")
        );
    }

    // Helper method to build category conditions SQL (PreparedStatement용)
    private StringBuilder buildCategoryConditionsSql(StringBuilder sql, String fcltName, String sidoCode,
                                              String toiletYn, String elevatorYn, String parkingYn,
                                              String wheelchairRentYn, String tactileMapYn, String audioGuideYn) {

        // 인덱스 활용을 위한 조건 순서 최적화
        if (fcltName != null && !fcltName.trim().isEmpty()) {
            sql.append("AND fclt_name LIKE '%").append(RepositoryUtils.escapeSql(fcltName)).append("%' ");
        }
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            sql.append("AND sido_code = '").append(RepositoryUtils.escapeSql(sidoCode)).append("' ");
        }
        if (toiletYn != null && !toiletYn.trim().isEmpty()) {
            sql.append("AND toilet_yn = '").append(RepositoryUtils.escapeSql(toiletYn)).append("' ");
        }
        if (elevatorYn != null && !elevatorYn.trim().isEmpty()) {
            sql.append("AND elevator_yn = '").append(RepositoryUtils.escapeSql(elevatorYn)).append("' ");
        }
        if (parkingYn != null && !parkingYn.trim().isEmpty()) {
            sql.append("AND parking_yn = '").append(RepositoryUtils.escapeSql(parkingYn)).append("' ");
        }
        if (wheelchairRentYn != null && !wheelchairRentYn.trim().isEmpty()) {
            sql.append("AND wheelchair_rent_yn = '").append(RepositoryUtils.escapeSql(wheelchairRentYn)).append("' ");
        }
        if (tactileMapYn != null && !tactileMapYn.trim().isEmpty()) {
            sql.append("AND tactile_map_yn = '").append(RepositoryUtils.escapeSql(tactileMapYn)).append("' ");
        }
        if (audioGuideYn != null && !audioGuideYn.trim().isEmpty()) {
            sql.append("AND audio_guide_yn = '").append(RepositoryUtils.escapeSql(audioGuideYn)).append("' ");
        }


        return sql;
    }
} 