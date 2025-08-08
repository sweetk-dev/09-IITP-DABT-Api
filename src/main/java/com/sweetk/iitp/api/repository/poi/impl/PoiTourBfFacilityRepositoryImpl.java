package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.config.DistanceCalculationConfig;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;
import com.sweetk.iitp.api.dto.poi.PoiTourBfFacilityLocation;
import com.sweetk.iitp.api.entity.poi.PoiTourBfFacilityEntity;
import com.sweetk.iitp.api.util.RepositoryUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    // Common SQL constants
    private static final String FACILITY_QUERY = "SELECT " + FACILITY_COMMON_COLUMNS + " " +
            "FROM poi_tour_bf_facility WHERE del_yn = 'N' ";

    private static final String FACILITY_QUERY_WITH_COUNT = "SELECT " + FACILITY_COMMON_COLUMNS + ", " +
            FACILITY_TOT_CNT_COLUMN +
            "FROM poi_tour_bf_facility WHERE del_yn = 'N' ";

    // 위치 검색용 기본 쿼리 (거리 계산 포함)
    private final String FACILITY_LOCATION_QUERY;
    private final String FACILITY_LOCATION_QUERY_WITH_COUNT;
    
    

    private static final String FACILITY_ORDER_BY = "ORDER BY fclt_name ";
    private static final String FACILITY_ORDER_BY_DISTANCE = "ORDER BY distance";

    public PoiTourBfFacilityRepositoryImpl(DistanceCalculationConfig distanceConfig) {
        this.distanceConfig = distanceConfig;
        
        // 기본 위치 검색 쿼리 생성
        this.FACILITY_LOCATION_QUERY = "SELECT " + FACILITY_COMMON_COLUMNS + ", " +
                distanceConfig.getDistanceCalculationSql() + " AS distance " +
                "FROM poi_tour_bf_facility WHERE del_yn = 'N' ";

        this.FACILITY_LOCATION_QUERY_WITH_COUNT = "SELECT " + FACILITY_COMMON_COLUMNS + ", " +
                distanceConfig.getDistanceCalculationSql() + " AS distance, " +
                FACILITY_TOT_CNT_COLUMN  +
                "FROM poi_tour_bf_facility WHERE del_yn = 'N' ";
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
    private String buildCategoryConditionsSql(String fcltName, String sidoCode,
                                                   String toiletYn, String elevatorYn, String parkingYn, 
                                                   String wheelchairRentYn, String tactileMapYn, String audioGuideYn) {
        StringBuilder sql = new StringBuilder();
        
        // 인덱스 활용을 위한 조건 순서 최적화
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            sql.append("AND sido_code = ? ");
        }
        if (toiletYn != null && !toiletYn.trim().isEmpty()) {
            sql.append("AND toilet_yn = ? ");
        }
        if (elevatorYn != null && !elevatorYn.trim().isEmpty()) {
            sql.append("AND elevator_yn = ? ");
        }
        if (parkingYn != null && !parkingYn.trim().isEmpty()) {
            sql.append("AND parking_yn = ? ");
        }
        if (wheelchairRentYn != null && !wheelchairRentYn.trim().isEmpty()) {
            sql.append("AND wheelchair_rent_yn = ? ");
        }
        if (tactileMapYn != null && !tactileMapYn.trim().isEmpty()) {
            sql.append("AND tactile_map_yn = ? ");
        }
        if (audioGuideYn != null && !audioGuideYn.trim().isEmpty()) {
            sql.append("AND audio_guide_yn = ? ");
        }
        if (fcltName != null && !fcltName.trim().isEmpty()) {
            sql.append("AND fclt_name LIKE ? ");
        }
        
        return sql.toString();
    }

    private String addPagingToQuery(String baseQuery, int offset, int size) {
        return baseQuery + " OFFSET " + offset + " LIMIT " + size;
    }

    @Override
    public java.util.Optional<PoiTourBfFacility> findByIdToDto(Integer fcltId) {
        String sql = FACILITY_QUERY + "AND fclt_id = ? ";

        log.debug("[PoiTourBfFacility] ID별 조회 쿼리: {}", sql);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, fcltId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                    return java.util.Optional.of(facility);
                }
                return java.util.Optional.empty();
            }
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] ID별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
    }

    @Override
    public List<PoiTourBfFacility> findBySidoCodeToDto(String sidoCode) {
        String sql = FACILITY_QUERY + "AND sido_code = ? " + FACILITY_ORDER_BY;

        log.debug("[PoiTourBfFacility] 시도별 조회 쿼리: {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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

    @Override
    public List<PoiTourBfFacility> findByCategoryConditions(String fcltName, String sidoCode, 
                                                          String toiletYn, String elevatorYn, 
                                                          String parkingYn, String wheelchairRentYn, 
                                                          String tactileMapYn, String audioGuideYn, 
                                                          Integer offset, Integer size) {
        StringBuilder sql = new StringBuilder(FACILITY_QUERY);
        sql.append(buildCategoryConditionsSql(fcltName, sidoCode, toiletYn, elevatorYn, parkingYn, 
                                       wheelchairRentYn, tactileMapYn, audioGuideYn));
        sql.append(FACILITY_ORDER_BY);

        log.debug("[PoiTourBfFacility] 카테고리 조건 검색 쿼리: {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (sidoCode != null && !sidoCode.trim().isEmpty()) {
                ps.setString(paramIndex++, sidoCode);
            }
            if (toiletYn != null && !toiletYn.trim().isEmpty()) {
                ps.setString(paramIndex++, toiletYn);
            }
            if (elevatorYn != null && !elevatorYn.trim().isEmpty()) {
                ps.setString(paramIndex++, elevatorYn);
            }
            if (parkingYn != null && !parkingYn.trim().isEmpty()) {
                ps.setString(paramIndex++, parkingYn);
            }
            if (wheelchairRentYn != null && !wheelchairRentYn.trim().isEmpty()) {
                ps.setString(paramIndex++, wheelchairRentYn);
            }
            if (tactileMapYn != null && !tactileMapYn.trim().isEmpty()) {
                ps.setString(paramIndex++, tactileMapYn);
            }
            if (audioGuideYn != null && !audioGuideYn.trim().isEmpty()) {
                ps.setString(paramIndex++, audioGuideYn);
            }
            if (fcltName != null && !fcltName.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + fcltName + "%");
            }

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

    @Override
    public PoiPageResult<PoiTourBfFacility> findByCategoryConditionsWithCount(
            String fcltName, String sidoCode, String toiletYn, String elevatorYn, 
            String parkingYn, String wheelchairRentYn, String tactileMapYn, String audioGuideYn, 
            int offset, int size) {
        StringBuilder sql = new StringBuilder(FACILITY_QUERY_WITH_COUNT);
        sql.append(buildCategoryConditionsSql(fcltName, sidoCode, toiletYn, elevatorYn, parkingYn, 
                                       wheelchairRentYn, tactileMapYn, audioGuideYn));
        sql.append(FACILITY_ORDER_BY);
        sql = new StringBuilder(addPagingToQuery(sql.toString(), offset, size));

        log.debug("[PoiTourBfFacility] 카테고리 조건 검색 쿼리 (페이징): {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (sidoCode != null && !sidoCode.trim().isEmpty()) {
                ps.setString(paramIndex++, sidoCode);
            }
            if (toiletYn != null && !toiletYn.trim().isEmpty()) {
                ps.setString(paramIndex++, toiletYn);
            }
            if (elevatorYn != null && !elevatorYn.trim().isEmpty()) {
                ps.setString(paramIndex++, elevatorYn);
            }
            if (parkingYn != null && !parkingYn.trim().isEmpty()) {
                ps.setString(paramIndex++, parkingYn);
            }
            if (wheelchairRentYn != null && !wheelchairRentYn.trim().isEmpty()) {
                ps.setString(paramIndex++, wheelchairRentYn);
            }
            if (tactileMapYn != null && !tactileMapYn.trim().isEmpty()) {
                ps.setString(paramIndex++, tactileMapYn);
            }
            if (audioGuideYn != null && !audioGuideYn.trim().isEmpty()) {
                ps.setString(paramIndex++, audioGuideYn);
            }
            if (fcltName != null && !fcltName.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + fcltName + "%");
            }

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

    @Override
    public List<PoiTourBfFacility> findByLocation(BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {
        String sql = FACILITY_QUERY + 
            "AND ST_DWithin(" +
            "ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, " +
            "ST_SetSRID(ST_MakePoint(?, ?), 4326)::geography, ?) " +
            FACILITY_ORDER_BY;

        log.debug("[PoiTourBfFacility] 위치 기반 검색 쿼리: {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            ps.setBigDecimal(3, radius.multiply(new BigDecimal(1000))); // 미터 단위로 변환
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                    entityList.add(facility);
                }
            }
            log.debug("[PoiTourBfFacility] 위치 기반 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 위치 기반 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database location query failed", e);
        }
        
        return entityList;
    }

    @Override
    public PoiPageResult<PoiTourBfFacility> findByLocationWithPagingCount(BigDecimal latitude, BigDecimal longitude,
                                                                         BigDecimal radius, int offset, int size) {
        String sql = FACILITY_QUERY_WITH_COUNT + 
            "AND ST_DWithin(" +
            "ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, " +
            "ST_SetSRID(ST_MakePoint(?, ?), 4326)::geography, ?) " +
            FACILITY_ORDER_BY + " OFFSET ? LIMIT ?";

        log.debug("[PoiTourBfFacility] 위치 기반 검색 쿼리 (페이징): {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            ps.setBigDecimal(3, radius.multiply(new BigDecimal(1000))); // 미터 단위로 변환
            ps.setInt(4, offset);
            ps.setInt(5, size);
            
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
            log.debug("[PoiTourBfFacility] 위치 기반 검색 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 위치 기반 검색 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database location query failed", e);
        }
        
        return new PoiPageResult<>(entityList, totalCount);
    }

    @Override
    public List<PoiTourBfFacilityEntity> findBySidoCode(String sidoCode) {
        String jpql = "SELECT p FROM PoiTourBfFacilityEntity p WHERE p.sidoCode = :sidoCode AND p.delYn = 'N'";
        TypedQuery<PoiTourBfFacilityEntity> query = entityManager.createQuery(jpql, PoiTourBfFacilityEntity.class);
        query.setParameter("sidoCode", sidoCode);
        return query.getResultList();
    }

    @Override
    public List<PoiTourBfFacilityEntity> findByFcltNameContaining(String fcltName) {
        String jpql = "SELECT p FROM PoiTourBfFacilityEntity p WHERE p.fcltName LIKE :fcltName AND p.delYn = 'N'";
        TypedQuery<PoiTourBfFacilityEntity> query = entityManager.createQuery(jpql, PoiTourBfFacilityEntity.class);
        query.setParameter("fcltName", "%" + fcltName + "%");
        return query.getResultList();
    }

    @Override
    public List<PoiTourBfFacilityEntity> findByLocationRange(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        String jpql = "SELECT p FROM PoiTourBfFacilityEntity p " +
                     "WHERE p.latitude BETWEEN :minLat AND :maxLat " +
                     "AND p.longitude BETWEEN :minLng AND :maxLng " +
                     "AND p.delYn = 'N'";
        TypedQuery<PoiTourBfFacilityEntity> query = entityManager.createQuery(jpql, PoiTourBfFacilityEntity.class);
        query.setParameter("minLat", minLat);
        query.setParameter("maxLat", maxLat);
        query.setParameter("minLng", minLng);
        query.setParameter("maxLng", maxLng);
        return query.getResultList();
    }

    @Override
    public List<PoiTourBfFacilityEntity> findByFacilityType(String facilityType, String ynValue) {
        String jpql = "SELECT p FROM PoiTourBfFacilityEntity p WHERE p." + facilityType + " = :ynValue AND p.delYn = 'N'";
        TypedQuery<PoiTourBfFacilityEntity> query = entityManager.createQuery(jpql, PoiTourBfFacilityEntity.class);
        query.setParameter("ynValue", ynValue);
        return query.getResultList();
    }

    @Override
    public Page<PoiTourBfFacilityEntity> findAllWithPagination(Pageable pageable) {
        String countJpql = "SELECT COUNT(p) FROM PoiTourBfFacilityEntity p WHERE p.delYn = 'N'";
        String dataJpql = "SELECT p FROM PoiTourBfFacilityEntity p WHERE p.delYn = 'N' ORDER BY p.fcltId";

        // Count query
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        Long total = countQuery.getSingleResult();

        // Data query
        TypedQuery<PoiTourBfFacilityEntity> dataQuery = entityManager.createQuery(dataJpql, PoiTourBfFacilityEntity.class);
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());
        List<PoiTourBfFacilityEntity> content = dataQuery.getResultList();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<PoiTourBfFacilityEntity> findByMultipleConditions(String sidoCode, String fcltName, 
                                                                String toiletYn, String elevatorYn, 
                                                                String parkingYn, String slopeYn) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM PoiTourBfFacilityEntity p WHERE p.delYn = 'N'");
        
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            jpql.append(" AND p.sidoCode = :sidoCode");
        }
        if (fcltName != null && !fcltName.trim().isEmpty()) {
            jpql.append(" AND p.fcltName LIKE :fcltName");
        }
        if (toiletYn != null && !toiletYn.trim().isEmpty()) {
            jpql.append(" AND p.toiletYn = :toiletYn");
        }
        if (elevatorYn != null && !elevatorYn.trim().isEmpty()) {
            jpql.append(" AND p.elevatorYn = :elevatorYn");
        }
        if (parkingYn != null && !parkingYn.trim().isEmpty()) {
            jpql.append(" AND p.parkingYn = :parkingYn");
        }
        if (slopeYn != null && !slopeYn.trim().isEmpty()) {
            jpql.append(" AND p.slopeYn = :slopeYn");
        }
        
        jpql.append(" ORDER BY p.fcltId");
        
        TypedQuery<PoiTourBfFacilityEntity> query = entityManager.createQuery(jpql.toString(), PoiTourBfFacilityEntity.class);
        
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            query.setParameter("sidoCode", sidoCode);
        }
        if (fcltName != null && !fcltName.trim().isEmpty()) {
            query.setParameter("fcltName", "%" + fcltName + "%");
        }
        if (toiletYn != null && !toiletYn.trim().isEmpty()) {
            query.setParameter("toiletYn", toiletYn);
        }
        if (elevatorYn != null && !elevatorYn.trim().isEmpty()) {
            query.setParameter("elevatorYn", elevatorYn);
        }
        if (parkingYn != null && !parkingYn.trim().isEmpty()) {
            query.setParameter("parkingYn", parkingYn);
        }
        if (slopeYn != null && !slopeYn.trim().isEmpty()) {
            query.setParameter("slopeYn", slopeYn);
        }
        
        return query.getResultList();
    }

    @Override
    public PoiPageResult<PoiTourBfFacility> findBySidoCodeWithPagingCount(String sidoCode, int offset, int size) {
        String sql = FACILITY_QUERY_WITH_COUNT + 
            "AND sido_code = ? " + FACILITY_ORDER_BY + " OFFSET ? LIMIT ?";

        log.debug("[PoiTourBfFacility] 시도별 조회 쿼리 (페이징): {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, sidoCode);
            ps.setInt(2, offset);
            ps.setInt(3, size);
            
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

    @Override
    public PoiPageResult<PoiTourBfFacility> findAllWithPagingCount(int offset, int size) {
        String sql = FACILITY_QUERY_WITH_COUNT + FACILITY_ORDER_BY + " OFFSET " + offset + " LIMIT " + size;

        log.debug("[PoiTourBfFacility] 전체 조회 쿼리 (페이징): {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
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

    /*******************************
     **  거리 정보 포함 위치 기반 검색 무장애 관광지 시설 조회
     *******************************/

    @Override
    public List<PoiTourBfFacilityLocation> findByLocationWithDistance(BigDecimal latitude, BigDecimal longitude, 
                                                                     BigDecimal radius) {
        StringBuilder sql = new StringBuilder(FACILITY_LOCATION_QUERY);
        
        // 거리 필터링 조건 추가
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

    @Override
    public PoiPageResult<PoiTourBfFacilityLocation> findByLocationWithDistanceAndPagingCount(BigDecimal latitude, BigDecimal longitude,
                                                                                             BigDecimal radius, int offset, int size) {
        StringBuilder sql = new StringBuilder(FACILITY_LOCATION_QUERY_WITH_COUNT);
        
        // 거리 필터링 조건 추가
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
} 