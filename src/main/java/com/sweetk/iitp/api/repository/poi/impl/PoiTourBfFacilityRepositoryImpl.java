package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.poi.PoiTourBfFacility;
import com.sweetk.iitp.api.entity.poi.PoiTourBfFacilityEntity;
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
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PoiTourBfFacilityRepositoryImpl implements PoiTourBfFacilityRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(PoiTourBfFacilityRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    // Common SQL constants
    private static final String FACILITY_BASE_QUERY = 
        "SELECT fclt_id, sido_code, fclt_name, toilet_yn, elevator_yn, parking_yn, slope_yn, " +
        "subway_yn, bus_stop_yn, wheelchair_rent_yn, tactile_map_yn, audio_guide_yn, " +
        "nursing_room_yn, accessible_room_yn, stroller_rent_yn, addr_road, addr_jibun, " +
        "latitude, longitude, base_dt " +
        "FROM poi_tour_bf_facility WHERE del_yn = 'N' ";

    private static final String FACILITY_BASE_QUERY_WITH_COUNT = 
        "SELECT fclt_id, sido_code, fclt_name, toilet_yn, elevator_yn, parking_yn, slope_yn, " +
        "subway_yn, bus_stop_yn, wheelchair_rent_yn, tactile_map_yn, audio_guide_yn, " +
        "nursing_room_yn, accessible_room_yn, stroller_rent_yn, addr_road, addr_jibun, " +
        "latitude, longitude, base_dt, " +
        "COUNT(*) OVER() AS total_count " +
        "FROM poi_tour_bf_facility WHERE del_yn = 'N' ";

    private static final String FACILITY_ORDER_BY = "ORDER BY fclt_name ";

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

    // Helper method to build category conditions SQL
    private StringBuilder buildCategoryConditionsSql(String baseQuery, String fcltName, String sidoCode, 
                                                   String toiletYn, String elevatorYn, String parkingYn, 
                                                   String wheelchairRentYn, String tactileMapYn, String audioGuideYn) {
        StringBuilder sql = new StringBuilder(baseQuery);
        
        // 인덱스 활용을 위한 조건 순서 최적화
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            sql.append("AND sido_code = '").append(sidoCode.replace("'", "''")).append("' ");
        }
        if (toiletYn != null && !toiletYn.trim().isEmpty()) {
            sql.append("AND toilet_yn = '").append(toiletYn.replace("'", "''")).append("' ");
        }
        if (elevatorYn != null && !elevatorYn.trim().isEmpty()) {
            sql.append("AND elevator_yn = '").append(elevatorYn.replace("'", "''")).append("' ");
        }
        if (parkingYn != null && !parkingYn.trim().isEmpty()) {
            sql.append("AND parking_yn = '").append(parkingYn.replace("'", "''")).append("' ");
        }
        if (wheelchairRentYn != null && !wheelchairRentYn.trim().isEmpty()) {
            sql.append("AND wheelchair_rent_yn = '").append(wheelchairRentYn.replace("'", "''")).append("' ");
        }
        if (tactileMapYn != null && !tactileMapYn.trim().isEmpty()) {
            sql.append("AND tactile_map_yn = '").append(tactileMapYn.replace("'", "''")).append("' ");
        }
        if (audioGuideYn != null && !audioGuideYn.trim().isEmpty()) {
            sql.append("AND audio_guide_yn = '").append(audioGuideYn.replace("'", "''")).append("' ");
        }
        if (fcltName != null && !fcltName.trim().isEmpty()) {
            sql.append("AND fclt_name LIKE '%").append(fcltName.replace("'", "''")).append("%' ");
        }
        
        return sql;
    }

    // Helper method to add paging to query
    private String addPagingToQuery(String baseQuery, int offset, int size) {
        return baseQuery + " OFFSET " + offset + " LIMIT " + size;
    }

    @Override
    public java.util.Optional<PoiTourBfFacility> findByIdToDto(Integer fcltId) {
        StringBuilder sql = new StringBuilder(FACILITY_BASE_QUERY);
        sql.append("AND fclt_id = ").append(fcltId).append(" ");
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString());
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return java.util.Optional.of(setPoiTourBfFacility(rs));
            } else {
                return java.util.Optional.empty();
            }
        } catch (SQLException e) {
            log.error("무장애 관광지 시설 ID 조회 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("무장애 관광지 시설 조회 실패", e);
        }
    }

    @Override
    public List<PoiTourBfFacility> findBySidoCodeToDto(String sidoCode) {
        StringBuilder sql = new StringBuilder(FACILITY_BASE_QUERY);
        sql.append("AND sido_code = '").append(sidoCode.replace("'", "''")).append("' ");
        sql.append(FACILITY_ORDER_BY);

        log.debug("[PoiTourBfFacility] 시도별 조회 쿼리: {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                entityList.add(facility);
            }
            log.debug("[PoiTourBfFacility] 시도별 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 시도별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database sido query failed", e);
        }
        
        return entityList;
    }

    @Override
    public List<PoiTourBfFacility> findByCategoryConditions(String fcltName, String sidoCode, 
                                                           String toiletYn, String elevatorYn, 
                                                           String parkingYn, String wheelchairRentYn, 
                                                           String tactileMapYn, String audioGuideYn, 
                                                           Integer offset, Integer size) {
        StringBuilder sql = new StringBuilder(FACILITY_BASE_QUERY_WITH_COUNT);
        sql = buildCategoryConditionsSql(sql, fcltName, sidoCode, toiletYn, elevatorYn, 
                                       parkingYn, wheelchairRentYn, tactileMapYn, audioGuideYn);
        sql.append(FACILITY_ORDER_BY);
        
        if (offset != null && size != null) {
            sql = new StringBuilder(addPagingToQuery(sql.toString(), offset, size));
        }

        log.debug("[PoiTourBfFacility] 카테고리 검색 쿼리: {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                entityList.add(facility);
            }
            log.debug("[PoiTourBfFacility] 카테고리 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 카테고리 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database category query failed", e);
        }
        
        return entityList;
    }

    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiTourBfFacility> findByCategoryConditionsWithCount(
            String fcltName, String sidoCode, String toiletYn, String elevatorYn, 
            String parkingYn, String wheelchairRentYn, String tactileMapYn, String audioGuideYn, 
            int offset, int size) {
        StringBuilder sql = new StringBuilder(FACILITY_BASE_QUERY_WITH_COUNT);
        sql = buildCategoryConditionsSql(sql, fcltName, sidoCode, toiletYn, elevatorYn, 
                                       parkingYn, wheelchairRentYn, tactileMapYn, audioGuideYn);
        sql.append(FACILITY_ORDER_BY);
        sql = new StringBuilder(addPagingToQuery(sql.toString(), offset, size));

        log.debug("[PoiTourBfFacility] 카테고리 검색 쿼리 (페이징 + 카운트): {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                entityList.add(facility);
                // 첫 번째 행에서 total_count 가져오기
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
            }
            log.debug("[PoiTourBfFacility] 카테고리 검색 완료 (페이징 + 카운트) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 카테고리 검색 쿼리 실행 중 오류 발생 (페이징 + 카운트)", e);
            throw new RuntimeException("Database category query failed", e);
        }
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }

    @Override
    public List<PoiTourBfFacility> findByLocation(BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {
        String sql = FACILITY_BASE_QUERY + 
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
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiTourBfFacility> findByLocationWithPaging(BigDecimal latitude, BigDecimal longitude, 
                                                            BigDecimal radius, int offset, int size) {
        String sql = FACILITY_BASE_QUERY_WITH_COUNT + 
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
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
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
        
        if (sidoCode != null && !sidoCode.isEmpty()) {
            jpql.append(" AND p.sidoCode = :sidoCode");
        }
        if (fcltName != null && !fcltName.isEmpty()) {
            jpql.append(" AND p.fcltName LIKE :fcltName");
        }
        if (toiletYn != null && !toiletYn.isEmpty()) {
            jpql.append(" AND p.toiletYn = :toiletYn");
        }
        if (elevatorYn != null && !elevatorYn.isEmpty()) {
            jpql.append(" AND p.elevatorYn = :elevatorYn");
        }
        if (parkingYn != null && !parkingYn.isEmpty()) {
            jpql.append(" AND p.parkingYn = :parkingYn");
        }
        if (slopeYn != null && !slopeYn.isEmpty()) {
            jpql.append(" AND p.slopeYn = :slopeYn");
        }

        TypedQuery<PoiTourBfFacilityEntity> query = entityManager.createQuery(jpql.toString(), PoiTourBfFacilityEntity.class);
        
        if (sidoCode != null && !sidoCode.isEmpty()) {
            query.setParameter("sidoCode", sidoCode);
        }
        if (fcltName != null && !fcltName.isEmpty()) {
            query.setParameter("fcltName", "%" + fcltName + "%");
        }
        if (toiletYn != null && !toiletYn.isEmpty()) {
            query.setParameter("toiletYn", toiletYn);
        }
        if (elevatorYn != null && !elevatorYn.isEmpty()) {
            query.setParameter("elevatorYn", elevatorYn);
        }
        if (parkingYn != null && !parkingYn.isEmpty()) {
            query.setParameter("parkingYn", parkingYn);
        }
        if (slopeYn != null && !slopeYn.isEmpty()) {
            query.setParameter("slopeYn", slopeYn);
        }

        return query.getResultList();
    }

    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiTourBfFacility> findBySidoCodeWithPaging(String sidoCode, int offset, int size) {
        String sql = FACILITY_BASE_QUERY_WITH_COUNT + 
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
            throw new RuntimeException("Database sido query failed", e);
        }
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }


    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiTourBfFacility> findAllWithPaging(int offset, int size) {
        String sql = FACILITY_BASE_QUERY_WITH_COUNT + FACILITY_ORDER_BY + " OFFSET " + offset + " LIMIT " + size;

        log.debug("[PoiTourBfFacility] 전체 조회 쿼리 (페이징): {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                entityList.add(facility);
                // 첫 번째 행에서 total_count 가져오기
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
            }
            log.debug("[PoiTourBfFacility] 전체 조회 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 전체 조회 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database all query failed", e);
        }
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }

    @Override
    public List<PoiTourBfFacility> findAllToDto() {
        String sql = FACILITY_BASE_QUERY + FACILITY_ORDER_BY;

        log.debug("[PoiTourBfFacility] 전체 조회 쿼리: {}", sql);

        List<PoiTourBfFacility> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PoiTourBfFacility facility = setPoiTourBfFacility(rs);
                entityList.add(facility);
            }
            log.debug("[PoiTourBfFacility] 전체 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiTourBfFacility] 전체 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database findAll query failed", e);
        }
        
        return entityList;
    }
} 