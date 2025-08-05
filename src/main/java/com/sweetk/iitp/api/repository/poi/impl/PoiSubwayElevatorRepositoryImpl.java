package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.dto.poi.PoiSubwayElevator;
import com.sweetk.iitp.api.entity.poi.PoiSubwayElevatorEntity;
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
import java.util.ArrayList;
import java.util.List;

@Repository
public class PoiSubwayElevatorRepositoryImpl implements PoiSubwayElevatorRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(PoiSubwayElevatorRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<PoiSubwayElevator> findByCategoryConditions(String stationName, String sidoCode, 
                                                           Integer nodeTypeCode, 
                                                           int offset, int size) {
        StringBuilder sql = new StringBuilder("SELECT " +
            "subway_id, sido_code, node_link_type, node_wkt, node_id, node_type_code, node_type_name, " +
            "sigungu_code, sigungu_name, eupmyeondong_code, eupmyeondong_name, station_code, station_name, " +
            "latitude, longitude, base_dt, " +
            "COUNT(*) OVER() AS total_count " +
            "FROM poi_subway_elevator WHERE del_yn = 'N' ");
        
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
        
        sql.append("ORDER BY station_name OFFSET ").append(offset).append(" LIMIT ").append(size);

        log.debug("[PoiSubwayElevator] 카테고리 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = new PoiSubwayElevator(
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
                entityList.add(elevator);
            }
            log.debug("[PoiSubwayElevator] 카테고리 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 카테고리 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    @Override
    public long countByCategoryConditions(String stationName, String sidoCode, 
                                        Integer nodeTypeCode) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM poi_subway_elevator WHERE del_yn = 'N'");
        
        // 인덱스 활용을 위한 조건 순서 최적화
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            sql.append(" AND sido_code = '").append(sidoCode.replace("'", "''")).append("'");
        }
        if (nodeTypeCode != null) {
            sql.append(" AND node_type_code = ").append(nodeTypeCode);
        }
        if (stationName != null && !stationName.trim().isEmpty()) {
            sql.append(" AND station_name LIKE '%").append(stationName.replace("'", "''")).append("%'");
        }

        log.debug("[PoiSubwayElevator] 카테고리 Count 쿼리: {}", sql);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0L;
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 카테고리 Count 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database count query failed", e);
        }
    }

    @Override
    public List<PoiSubwayElevator> findBySigunguConditions(String sidoCode, String sigunguCode, 
                                                           int offset, int size) {
        StringBuilder sql = new StringBuilder("SELECT " +
            "subway_id, sido_code, node_link_type, node_wkt, node_id, node_type_code, node_type_name, " +
            "sigungu_code, sigungu_name, eupmyeondong_code, eupmyeondong_name, station_code, station_name, " +
            "latitude, longitude, base_dt, " +
            "COUNT(*) OVER() AS total_count " +
            "FROM poi_subway_elevator WHERE del_yn = 'N' " +
            "AND sido_code = '").append(sidoCode.replace("'", "''")).append("' " +
            "AND sigungu_code = '").append(sigunguCode.replace("'", "''")).append("' " +
            "ORDER BY station_name OFFSET ").append(offset).append(" LIMIT ").append(size);

        log.debug("[PoiSubwayElevator] 시군구 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiSubwayElevator elevator = new PoiSubwayElevator(
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
    public long countBySigunguConditions(String sidoCode, String sigunguCode) {
        String sql = "SELECT COUNT(*) FROM poi_subway_elevator " +
                    "WHERE del_yn = 'N' " +
                    "AND sido_code = '" + sidoCode.replace("'", "''") + "' " +
                    "AND sigungu_code = '" + sigunguCode.replace("'", "''") + "'";

        log.debug("[PoiSubwayElevator] 시군구 Count 쿼리: {}", sql);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0L;
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 시군구 Count 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database count query failed", e);
        }
    }

    @Override
    public List<PoiSubwayElevator> findByLocationWithPaging(BigDecimal latitude, BigDecimal longitude, 
                                                            BigDecimal radius, int offset, int size) {
        String sql = "SELECT " +
            "subway_id, sido_code, node_link_type, node_wkt, node_id, node_type_code, node_type_name, " +
            "sigungu_code, sigungu_name, eupmyeondong_code, eupmyeondong_name, station_code, station_name, " +
            "latitude, longitude, base_dt, " +
            "COUNT(*) OVER() AS total_count " +
            "FROM poi_subway_elevator " +
            "WHERE del_yn = 'N' " +
            "AND ST_DWithin(" +
            "ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, " +
            "ST_SetSRID(ST_MakePoint(?, ?), 4326)::geography, ?) " +
            "ORDER BY station_name OFFSET ? LIMIT ?";

        log.debug("[PoiSubwayElevator] 위치 기반 검색 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            ps.setBigDecimal(3, radius.multiply(new BigDecimal(1000))); // 미터 단위로 변환
            ps.setInt(4, offset);
            ps.setInt(5, size);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = new PoiSubwayElevator(
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
                    entityList.add(elevator);
                }
            }
            log.debug("[PoiSubwayElevator] 위치 기반 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 위치 기반 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database location query failed", e);
        }
        
        return entityList;
    }

    @Override
    public long countByLocation(BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {
        String sql = "SELECT COUNT(*) FROM poi_subway_elevator " +
                    "WHERE del_yn = 'N' " +
                    "AND ST_DWithin(" +
                    "ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, " +
                    "ST_SetSRID(ST_MakePoint(?, ?), 4326)::geography, ?)";

        log.debug("[PoiSubwayElevator] 위치 기반 Count 쿼리: {}", sql);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            ps.setBigDecimal(3, radius.multiply(new BigDecimal(1000))); // 미터 단위로 변환
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                return 0L;
            }
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 위치 기반 Count 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database location count query failed", e);
        }
    }

    @Override
    public List<PoiSubwayElevatorEntity> findBySidoCode(String sidoCode) {
        String jpql = "SELECT p FROM PoiSubwayElevatorEntity p WHERE p.sidoCode = :sidoCode AND p.delYn = 'N'";
        TypedQuery<PoiSubwayElevatorEntity> query = entityManager.createQuery(jpql, PoiSubwayElevatorEntity.class);
        query.setParameter("sidoCode", sidoCode);
        return query.getResultList();
    }

    @Override
    public List<PoiSubwayElevatorEntity> findByStationCode(String stationCode) {
        String jpql = "SELECT p FROM PoiSubwayElevatorEntity p WHERE p.stationCode = :stationCode AND p.delYn = 'N'";
        TypedQuery<PoiSubwayElevatorEntity> query = entityManager.createQuery(jpql, PoiSubwayElevatorEntity.class);
        query.setParameter("stationCode", stationCode);
        return query.getResultList();
    }

    @Override
    public List<PoiSubwayElevatorEntity> findByLocationRange(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        String jpql = "SELECT p FROM PoiSubwayElevatorEntity p " +
                     "WHERE p.latitude BETWEEN :minLat AND :maxLat " +
                     "AND p.longitude BETWEEN :minLng AND :maxLng " +
                     "AND p.delYn = 'N'";
        TypedQuery<PoiSubwayElevatorEntity> query = entityManager.createQuery(jpql, PoiSubwayElevatorEntity.class);
        query.setParameter("minLat", minLat);
        query.setParameter("maxLat", maxLat);
        query.setParameter("minLng", minLng);
        query.setParameter("maxLng", maxLng);
        return query.getResultList();
    }

    @Override
    public List<PoiSubwayElevatorEntity> findByNodeTypeCode(Integer nodeTypeCode) {
        String jpql = "SELECT p FROM PoiSubwayElevatorEntity p WHERE p.nodeTypeCode = :nodeTypeCode AND p.delYn = 'N'";
        TypedQuery<PoiSubwayElevatorEntity> query = entityManager.createQuery(jpql, PoiSubwayElevatorEntity.class);
        query.setParameter("nodeTypeCode", nodeTypeCode);
        return query.getResultList();
    }

    @Override
    public Page<PoiSubwayElevatorEntity> findAllWithPagination(Pageable pageable) {
        String countJpql = "SELECT COUNT(p) FROM PoiSubwayElevatorEntity p WHERE p.delYn = 'N'";
        String dataJpql = "SELECT p FROM PoiSubwayElevatorEntity p WHERE p.delYn = 'N' ORDER BY p.subwayId";

        // Count query
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        Long total = countQuery.getSingleResult();

        // Data query
        TypedQuery<PoiSubwayElevatorEntity> dataQuery = entityManager.createQuery(dataJpql, PoiSubwayElevatorEntity.class);
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());
        List<PoiSubwayElevatorEntity> content = dataQuery.getResultList();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<PoiSubwayElevator> findBySidoCodeWithPaging(String sidoCode, int offset, int size) {
        String sql = "SELECT subway_id, sido_code, node_link_type, node_wkt, node_id, node_type_code, node_type_name, " +
            "sigungu_code, sigungu_name, eupmyeondong_code, eupmyeondong_name, station_code, station_name, " +
            "latitude, longitude, base_dt " +
            "FROM poi_subway_elevator " +
            "WHERE sido_code = ? AND del_yn = 'N' " +
            "ORDER BY station_name OFFSET ? LIMIT ?";

        log.debug("[PoiSubwayElevator] 시도별 조회 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, sidoCode);
            ps.setInt(2, offset);
            ps.setInt(3, size);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = new PoiSubwayElevator(
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
                    entityList.add(elevator);
                }
            }
            log.debug("[PoiSubwayElevator] 시도별 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 시도별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database sido query failed", e);
        }
        
        return entityList;
    }

    @Override
    public long countBySidoCode(String sidoCode) {
        String sql = "SELECT COUNT(*) FROM poi_subway_elevator WHERE sido_code = ? AND del_yn = 'N'";

        log.debug("[PoiSubwayElevator] 시도별 Count 쿼리: {}", sql);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, sidoCode);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                return 0L;
            }
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 시도별 Count 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database sido count query failed", e);
        }
    }

    @Override
    public List<PoiSubwayElevator> findAllWithPaging(int offset, int size) {
        String sql = "SELECT subway_id, sido_code, node_link_type, node_wkt, node_id, node_type_code, node_type_name, " +
            "sigungu_code, sigungu_name, eupmyeondong_code, eupmyeondong_name, station_code, station_name, " +
            "latitude, longitude, base_dt " +
            "FROM poi_subway_elevator " +
            "WHERE del_yn = 'N' " +
            "ORDER BY station_name OFFSET ? LIMIT ?";

        log.debug("[PoiSubwayElevator] 전체 조회 쿼리: {}", sql);

        List<PoiSubwayElevator> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, offset);
            ps.setInt(2, size);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiSubwayElevator elevator = new PoiSubwayElevator(
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
                    entityList.add(elevator);
                }
            }
            log.debug("[PoiSubwayElevator] 전체 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 전체 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database all query failed", e);
        }
        
        return entityList;
    }

    @Override
    public long countAll() {
        String sql = "SELECT COUNT(*) FROM poi_subway_elevator WHERE del_yn = 'N'";

        log.debug("[PoiSubwayElevator] 전체 Count 쿼리: {}", sql);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                return 0L;
            }
        } catch (SQLException e) {
            log.error("[PoiSubwayElevator] 전체 Count 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database all count query failed", e);
        }
    }
} 