package com.sweetk.iitp.api.repository.poi.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfo;
import com.sweetk.iitp.api.dto.poi.converter.PoiPublicToiletInfoConverter;
import com.sweetk.iitp.api.entity.poi.PoiPublicToiletInfoEntity;
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
import java.util.stream.Collectors;

@Repository
public class PoiPublicToiletInfoRepositoryImpl implements PoiPublicToiletInfoRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(PoiPublicToiletInfoRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<PoiPublicToiletInfoEntity> findBySidoCode(String sidoCode) {
        String jpql = "SELECT p FROM PoiPublicToiletInfoEntity p WHERE p.sidoCode = :sidoCode AND p.delYn = 'N'";
        TypedQuery<PoiPublicToiletInfoEntity> query = entityManager.createQuery(jpql, PoiPublicToiletInfoEntity.class);
        query.setParameter("sidoCode", sidoCode);
        return query.getResultList();
    }

    @Override
    public List<PoiPublicToiletInfoEntity> findByToiletType(String toiletType) {
        String jpql = "SELECT p FROM PoiPublicToiletInfoEntity p WHERE p.toiletType = :toiletType AND p.delYn = 'N'";
        TypedQuery<PoiPublicToiletInfoEntity> query = entityManager.createQuery(jpql, PoiPublicToiletInfoEntity.class);
        query.setParameter("toiletType", toiletType);
        return query.getResultList();
    }

    @Override
    public List<PoiPublicToiletInfoEntity> findByToiletNameContaining(String toiletName) {
        String jpql = "SELECT p FROM PoiPublicToiletInfoEntity p WHERE p.toiletName LIKE :toiletName AND p.delYn = 'N'";
        TypedQuery<PoiPublicToiletInfoEntity> query = entityManager.createQuery(jpql, PoiPublicToiletInfoEntity.class);
        query.setParameter("toiletName", "%" + toiletName + "%");
        return query.getResultList();
    }

    @Override
    public List<PoiPublicToiletInfoEntity> findByLocationRange(Double minLat, Double maxLat, Double minLng, Double maxLng) {
        String jpql = "SELECT p FROM PoiPublicToiletInfoEntity p " +
                     "WHERE p.latitude BETWEEN :minLat AND :maxLat " +
                     "AND p.longitude BETWEEN :minLng AND :maxLng " +
                     "AND p.delYn = 'N'";
        TypedQuery<PoiPublicToiletInfoEntity> query = entityManager.createQuery(jpql, PoiPublicToiletInfoEntity.class);
        query.setParameter("minLat", minLat);
        query.setParameter("maxLat", maxLat);
        query.setParameter("minLng", minLng);
        query.setParameter("maxLng", maxLng);
        return query.getResultList();
    }

    @Override
    public List<PoiPublicToiletInfoEntity> findByFacilityType(String facilityType, String ynValue) {
        String jpql = "SELECT p FROM PoiPublicToiletInfoEntity p WHERE p." + facilityType + " = :ynValue AND p.delYn = 'N'";
        TypedQuery<PoiPublicToiletInfoEntity> query = entityManager.createQuery(jpql, PoiPublicToiletInfoEntity.class);
        query.setParameter("ynValue", ynValue);
        return query.getResultList();
    }

    @Override
    public List<PoiPublicToiletInfoEntity> findByDisabilityFacility() {
        String jpql = "SELECT p FROM PoiPublicToiletInfoEntity p " +
                     "WHERE (p.mDisToiletCount > 0 OR p.mDisUrinalCount > 0 OR p.fDisToiletCount > 0) " +
                     "AND p.delYn = 'N'";
        TypedQuery<PoiPublicToiletInfoEntity> query = entityManager.createQuery(jpql, PoiPublicToiletInfoEntity.class);
        return query.getResultList();
    }

    @Override
    public List<PoiPublicToiletInfoEntity> findByChildFacility() {
        String jpql = "SELECT p FROM PoiPublicToiletInfoEntity p " +
                     "WHERE (p.mChildToiletCount > 0 OR p.mChildUrinalCount > 0 OR p.fChildToiletCount > 0) " +
                     "AND p.delYn = 'N'";
        TypedQuery<PoiPublicToiletInfoEntity> query = entityManager.createQuery(jpql, PoiPublicToiletInfoEntity.class);
        return query.getResultList();
    }

    @Override
    public List<PoiPublicToiletInfoEntity> findBy24HourOpen() {
        String jpql = "SELECT p FROM PoiPublicToiletInfoEntity p " +
                     "WHERE p.openTime LIKE '%24시간%' AND p.delYn = 'N'";
        TypedQuery<PoiPublicToiletInfoEntity> query = entityManager.createQuery(jpql, PoiPublicToiletInfoEntity.class);
        return query.getResultList();
    }

    @Override
    public Page<PoiPublicToiletInfoEntity> findAllWithPagination(Pageable pageable) {
        String countJpql = "SELECT COUNT(p) FROM PoiPublicToiletInfoEntity p WHERE p.delYn = 'N'";
        String dataJpql = "SELECT p FROM PoiPublicToiletInfoEntity p WHERE p.delYn = 'N' ORDER BY p.toiletId";

        // Count query
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        Long total = countQuery.getSingleResult();

        // Data query
        TypedQuery<PoiPublicToiletInfoEntity> dataQuery = entityManager.createQuery(dataJpql, PoiPublicToiletInfoEntity.class);
        dataQuery.setFirstResult((int) pageable.getOffset());
        dataQuery.setMaxResults(pageable.getPageSize());
        List<PoiPublicToiletInfoEntity> content = dataQuery.getResultList();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<PoiPublicToiletInfoEntity> findByMultipleConditions(String sidoCode, String toiletType, 
                                                                   String toiletName, String safetyTargetYn, 
                                                                   String emgBellYn, String cctvYn) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM PoiPublicToiletInfoEntity p WHERE p.delYn = 'N'");
        
        if (sidoCode != null && !sidoCode.isEmpty()) {
            jpql.append(" AND p.sidoCode = :sidoCode");
        }
        if (toiletType != null && !toiletType.isEmpty()) {
            jpql.append(" AND p.toiletType = :toiletType");
        }
        if (toiletName != null && !toiletName.isEmpty()) {
            jpql.append(" AND p.toiletName LIKE :toiletName");
        }
        if (safetyTargetYn != null && !safetyTargetYn.isEmpty()) {
            jpql.append(" AND p.safetyTargetYn = :safetyTargetYn");
        }
        if (emgBellYn != null && !emgBellYn.isEmpty()) {
            jpql.append(" AND p.emgBellYn = :emgBellYn");
        }
        if (cctvYn != null && !cctvYn.isEmpty()) {
            jpql.append(" AND p.cctvYn = :cctvYn");
        }

        TypedQuery<PoiPublicToiletInfoEntity> query = entityManager.createQuery(jpql.toString(), PoiPublicToiletInfoEntity.class);
        
        if (sidoCode != null && !sidoCode.isEmpty()) {
            query.setParameter("sidoCode", sidoCode);
        }
        if (toiletType != null && !toiletType.isEmpty()) {
            query.setParameter("toiletType", toiletType);
        }
        if (toiletName != null && !toiletName.isEmpty()) {
            query.setParameter("toiletName", "%" + toiletName + "%");
        }
        if (safetyTargetYn != null && !safetyTargetYn.isEmpty()) {
            query.setParameter("safetyTargetYn", safetyTargetYn);
        }
        if (emgBellYn != null && !emgBellYn.isEmpty()) {
            query.setParameter("emgBellYn", emgBellYn);
        }
        if (cctvYn != null && !cctvYn.isEmpty()) {
            query.setParameter("cctvYn", cctvYn);
        }

        return query.getResultList();
    }

    @Override
    public List<PoiPublicToiletInfo> findByCategoryConditions(String toiletName, String sidoCode, String toiletType, 
                                                             String disabilityFacilityYn, String open24hYn, 
                                                             int offset, int size) {
        StringBuilder sql = new StringBuilder("SELECT " +
            "toilet_id, sido_code, toilet_name, toilet_type, basis, addr_road, addr_jibun, " +
            "m_toilet_count, m_urinal_count, m_dis_toilet_count, m_dis_urinal_count, m_child_toilet_count, m_child_urinal_count, " +
            "f_toilet_count, f_urinal_count, f_dis_toilet_count, f_child_toilet_count, " +
            "latitude, longitude, open_time, safety_target_yn, emg_bell_yn, cctv_yn, diaper_table_yn, " +
            "COUNT(*) OVER() AS total_count " +
            "FROM poi_public_toilet_info WHERE del_yn = 'N' ");
        
        // 인덱스 활용을 위한 조건 순서 최적화
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            sql.append("AND sido_code = '").append(sidoCode.replace("'", "''")).append("' ");
        }
        if (toiletType != null && !toiletType.trim().isEmpty()) {
            sql.append("AND toilet_type = '").append(toiletType.replace("'", "''")).append("' ");
        }
        if (toiletName != null && !toiletName.trim().isEmpty()) {
            sql.append("AND toilet_name LIKE '%").append(toiletName.replace("'", "''")).append("%' ");
        }
        if ("Y".equals(disabilityFacilityYn)) {
            sql.append("AND (m_dis_toilet_count > 0 OR m_dis_urinal_count > 0 OR f_dis_toilet_count > 0) ");
        }
        if ("Y".equals(open24hYn)) {
            sql.append("AND open_time LIKE '%24시간%' ");
        }
        
        sql.append("ORDER BY toilet_name OFFSET ").append(offset).append(" LIMIT ").append(size);

        log.debug("[PoiPublicToiletInfo] 최종 실행 쿼리: {}", sql);

        List<PoiPublicToiletInfo> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiPublicToiletInfo toilet = new PoiPublicToiletInfo(
                    rs.getInt("toilet_id"),
                    rs.getString("sido_code"),
                    rs.getString("toilet_name"),
                    rs.getString("toilet_type"),
                    rs.getString("basis"),
                    rs.getString("addr_road"),
                    rs.getString("addr_jibun"),
                    rs.getInt("m_toilet_count"),
                    rs.getInt("m_urinal_count"),
                    rs.getInt("m_dis_toilet_count"),
                    rs.getInt("m_dis_urinal_count"),
                    rs.getInt("m_child_toilet_count"),
                    rs.getInt("m_child_urinal_count"),
                    rs.getInt("f_toilet_count"),
                    rs.getInt("f_dis_toilet_count"),
                    rs.getInt("f_child_toilet_count"),
                    null, // managingOrg
                    null, // phoneNumber
                    rs.getString("open_time"),
                    null, // openTimeDetail
                    null, // installDt
                    rs.getBigDecimal("latitude") != null ? rs.getBigDecimal("latitude").doubleValue() : null,
                    rs.getBigDecimal("longitude") != null ? rs.getBigDecimal("longitude").doubleValue() : null,
                    null, // ownerType
                    null, // wasteProcessType
                    rs.getString("safety_target_yn"),
                    rs.getString("emg_bell_yn"),
                    null, // emgBellLocation
                    rs.getString("cctv_yn"),
                    rs.getString("diaper_table_yn"),
                    null, // diaperTableLocation
                    null, // remodeledDt
                    null  // baseDt
                );
                entityList.add(toilet);
            }
            log.debug("[PoiPublicToiletInfo] JDBC 쿼리 실행 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] JDBC 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database query failed", e);
        }
        
        return entityList;
    }

    @Override
    public long countByCategoryConditions(String toiletName, String sidoCode, String toiletType, 
                                        String disabilityFacilityYn, String open24hYn) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM poi_public_toilet_info WHERE del_yn = 'N'");
        
        // 인덱스 활용을 위한 조건 순서 최적화
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            sql.append(" AND sido_code = '").append(sidoCode.replace("'", "''")).append("'");
        }
        if (toiletType != null && !toiletType.trim().isEmpty()) {
            sql.append(" AND toilet_type = '").append(toiletType.replace("'", "''")).append("'");
        }
        if (toiletName != null && !toiletName.trim().isEmpty()) {
            sql.append(" AND toilet_name LIKE '%").append(toiletName.replace("'", "''")).append("%'");
        }
        if ("Y".equals(disabilityFacilityYn)) {
            sql.append(" AND (m_dis_toilet_count > 0 OR m_dis_urinal_count > 0 OR f_dis_toilet_count > 0)");
        }
        if ("Y".equals(open24hYn)) {
            sql.append(" AND open_time LIKE '%24시간%'");
        }

        log.debug("[PoiPublicToiletInfo] Count 쿼리: {}", sql);

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0L;
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] Count 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database count query failed", e);
        }
    }

    @Override
    public List<PoiPublicToiletInfo> findByLocationWithPaging(BigDecimal latitude, BigDecimal longitude, 
                                                             BigDecimal radius, int offset, int size) {
        String sql = "SELECT " +
            "toilet_id, sido_code, toilet_name, toilet_type, basis, addr_road, addr_jibun, " +
            "m_toilet_count, m_urinal_count, m_dis_toilet_count, m_dis_urinal_count, m_child_toilet_count, m_child_urinal_count, " +
            "f_toilet_count, f_urinal_count, f_dis_toilet_count, f_child_toilet_count, " +
            "latitude, longitude, open_time, safety_target_yn, emg_bell_yn, cctv_yn, diaper_table_yn, " +
            "COUNT(*) OVER() AS total_count " +
            "FROM poi_public_toilet_info " +
            "WHERE del_yn = 'N' " +
            "AND ST_DWithin(" +
            "ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, " +
            "ST_SetSRID(ST_MakePoint(?, ?), 4326)::geography, ?) " +
            "ORDER BY toilet_name OFFSET ? LIMIT ?";

        log.debug("[PoiPublicToiletInfo] 위치 기반 검색 쿼리: {}", sql);

        List<PoiPublicToiletInfo> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            ps.setBigDecimal(3, radius.multiply(new BigDecimal(1000))); // 미터 단위로 변환
            ps.setInt(4, offset);
            ps.setInt(5, size);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiPublicToiletInfo toilet = new PoiPublicToiletInfo(
                        rs.getInt("toilet_id"),
                        rs.getString("sido_code"),
                        rs.getString("toilet_name"),
                        rs.getString("toilet_type"),
                        rs.getString("basis"),
                        rs.getString("addr_road"),
                        rs.getString("addr_jibun"),
                        rs.getInt("m_toilet_count"),
                        rs.getInt("m_urinal_count"),
                        rs.getInt("m_dis_toilet_count"),
                        rs.getInt("m_dis_urinal_count"),
                        rs.getInt("m_child_toilet_count"),
                        rs.getInt("m_child_urinal_count"),
                        rs.getInt("f_toilet_count"),
                        rs.getInt("f_dis_toilet_count"),
                        rs.getInt("f_child_toilet_count"),
                        null, // managingOrg
                        null, // phoneNumber
                        rs.getString("open_time"),
                        null, // openTimeDetail
                        null, // installDt
                        rs.getBigDecimal("latitude") != null ? rs.getBigDecimal("latitude").doubleValue() : null,
                        rs.getBigDecimal("longitude") != null ? rs.getBigDecimal("longitude").doubleValue() : null,
                        null, // ownerType
                        null, // wasteProcessType
                        rs.getString("safety_target_yn"),
                        rs.getString("emg_bell_yn"),
                        null, // emgBellLocation
                        rs.getString("cctv_yn"),
                        rs.getString("diaper_table_yn"),
                        null, // diaperTableLocation
                        null, // remodeledDt
                        null  // baseDt
                    );
                    entityList.add(toilet);
                }
            }
            log.debug("[PoiPublicToiletInfo] 위치 기반 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] 위치 기반 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database location query failed", e);
        }
        
        return entityList;
    }

    @Override
    public long countByLocation(BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {
        String sql = "SELECT COUNT(*) FROM poi_public_toilet_info " +
                    "WHERE del_yn = 'N' " +
                    "AND ST_DWithin(" +
                    "ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, " +
                    "ST_SetSRID(ST_MakePoint(?, ?), 4326)::geography, ?)";

        log.debug("[PoiPublicToiletInfo] 위치 기반 Count 쿼리: {}", sql);

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
            log.error("[PoiPublicToiletInfo] 위치 기반 Count 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database location count query failed", e);
        }
    }
} 