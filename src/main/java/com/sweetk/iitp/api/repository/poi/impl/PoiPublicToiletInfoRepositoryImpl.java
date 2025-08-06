package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.constant.poi.PoiPublicToiletType;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletInfo;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PoiPublicToiletInfoRepositoryImpl implements PoiPublicToiletInfoRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(PoiPublicToiletInfoRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    // Common SQL constants
    private static final String toiletBaseQuery = "SELECT toilet_id, sido_code, toilet_name, toilet_type, basis, addr_road, addr_jibun, " +
            "m_toilet_count, m_urinal_count, m_dis_toilet_count, m_dis_urinal_count, m_child_toilet_count, m_child_urinal_count, " +
            "f_toilet_count, f_urinal_count, f_dis_toilet_count, f_child_toilet_count, " +
            "managing_org, phone_number, open_time, open_time_detail, install_dt, " +
            "latitude, longitude, owner_type, waste_process_type, safety_target_yn, " +
            "emg_bell_yn, emg_bell_location, cctv_yn, diaper_table_yn, diaper_table_location, " +
            "remodeled_dt, base_dt " +
            "FROM poi_public_toilet_info WHERE del_yn = 'N' ";

    private static final String toiletBaseQueryWithDistance = toiletBaseQuery.replace("SELECT ", "SELECT ") +
            "ST_Distance_Sphere(POINT(longitude, latitude), POINT(?, ?)) AS distance " +
            "FROM poi_public_toilet_info WHERE del_yn = 'N' ";

    private static final String toiletOrderBy = "ORDER BY toilet_name";
    private static final String toiletOrderByDistance = "ORDER BY distance";

    // Helper method to add count to query
    private String addCountToQuery(String baseQuery) {
        return baseQuery.replace("SELECT ", "SELECT COUNT(*) OVER() AS total_count, ");
    }

    @Override
    public java.util.Optional<PoiPublicToiletInfo> findByIdToDto(Integer toiletId) {
        StringBuilder sql = new StringBuilder(toiletBaseQuery);
        sql.append("AND toilet_id = ").append(toiletId).append(" ");
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString());
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return java.util.Optional.of(setPoiPublicToiletInfo(rs));
            } else {
                return java.util.Optional.empty();
            }
        } catch (SQLException e) {
            log.error("공중 화장실 ID 조회 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("공중 화장실 조회 실패", e);
        }
    }

    // Helper method to create PoiPublicToiletInfo from ResultSet
    private PoiPublicToiletInfo setPoiPublicToiletInfo(ResultSet rs) throws SQLException {
        return new PoiPublicToiletInfo(
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
            rs.getString("managing_org"),
            rs.getString("phone_number"),
            rs.getString("open_time"),
            rs.getString("open_time_detail"),
            rs.getString("install_dt"),
            rs.getBigDecimal("latitude") != null ? rs.getBigDecimal("latitude").doubleValue() : null,
            rs.getBigDecimal("longitude") != null ? rs.getBigDecimal("longitude").doubleValue() : null,
            rs.getString("owner_type"),
            rs.getString("waste_process_type"),
            rs.getString("safety_target_yn"),
            rs.getString("emg_bell_yn"),
            rs.getString("emg_bell_location"),
            rs.getString("cctv_yn"),
            rs.getString("diaper_table_yn"),
            rs.getString("diaper_table_location"),
            rs.getString("remodeled_dt"),
            rs.getDate("base_dt") != null ? rs.getDate("base_dt").toLocalDate() : null
        );
    }

    // Helper method to build category conditions SQL
    private StringBuilder buildCategoryConditionsSql(StringBuilder sql, String toiletName, String sidoCode, 
                                                  PoiPublicToiletType toiletType, String open24hYn) {
        // 인덱스 활용을 위한 조건 순서 최적화
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            sql.append("AND sido_code = '").append(sidoCode.replace("'", "''")).append("' ");
        }
        if (toiletType != null) {
            sql.append("AND toilet_type = '").append(toiletType.getName().replace("'", "''")).append("' ");
        }
        if (toiletName != null && !toiletName.trim().isEmpty()) {
            sql.append("AND toilet_name LIKE '%").append(toiletName.replace("'", "''")).append("%' ");
        }
        if ("Y".equals(open24hYn)) {
            sql.append("AND open_time LIKE '%24시간%' ");
        }
        
        return sql;
    }



    @Override
    public List<PoiPublicToiletInfo> findBySidoCodeToDto(String sidoCode) {
        StringBuilder sql = new StringBuilder(toiletBaseQuery);
        sql.append("AND sido_code = '").append(sidoCode.replace("'", "''")).append("' ");
        sql.append(toiletOrderBy);

        log.debug("[PoiPublicToiletInfo] 시도별 조회 쿼리: {}", sql);

        List<PoiPublicToiletInfo> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiPublicToiletInfo toilet = setPoiPublicToiletInfo(rs);
                entityList.add(toilet);
            }
            log.debug("[PoiPublicToiletInfo] 시도별 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] 시도별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database sido query failed", e);
        }
        
        return entityList;
    }

    @Override
    public List<PoiPublicToiletInfo> findByCategoryConditions(String toiletName, String sidoCode, PoiPublicToiletType toiletType, 
                                                             String open24hYn, int offset, int size) {
        StringBuilder sql = new StringBuilder(toiletBaseQuery);
        sql = buildCategoryConditionsSql(sql, toiletName, sidoCode, toiletType, open24hYn);
        sql.append(toiletOrderBy);
        sql = new StringBuilder(addCountToQuery(sql.toString()));

        log.debug("[PoiPublicToiletInfo] 카테고리 조건 검색 쿼리: {}", sql);

        List<PoiPublicToiletInfo> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiPublicToiletInfo toilet = setPoiPublicToiletInfo(rs);
                entityList.add(toilet);
            }
            log.debug("[PoiPublicToiletInfo] 카테고리 조건 검색 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] 카테고리 조건 검색 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database category query failed", e);
        }
        
        return entityList;
    }



    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiPublicToiletInfo> findByLocationWithPaging(BigDecimal latitude, BigDecimal longitude, 
                                                             BigDecimal radius, String toiletName, PoiPublicToiletType toiletType, 
                                                             String open24hYn, int offset, int size) {
        StringBuilder sql = new StringBuilder(addCountToQuery(toiletBaseQuery));
        sql.append("AND ST_DWithin(" +
            "ST_SetSRID(ST_MakePoint(longitude, latitude), 4326)::geography, " +
            "ST_SetSRID(ST_MakePoint(?, ?), 4326)::geography, ?) ");
        
        // 추가 검색 조건 처리
        if (toiletName != null && !toiletName.trim().isEmpty()) {
            sql.append("AND toilet_name LIKE ? ");
        }
        if (toiletType != null) {
            sql.append("AND toilet_type = ? ");
        }
        if ("Y".equals(open24hYn)) {
            sql.append("AND open_time LIKE '%24시간%' ");
        }
        
        sql.append(toiletOrderBy).append(" OFFSET ? LIMIT ?");

        log.debug("[PoiPublicToiletInfo] 위치 기반 검색 쿼리 (통합): {}", sql);

        List<PoiPublicToiletInfo> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            ps.setBigDecimal(paramIndex++, longitude);
            ps.setBigDecimal(paramIndex++, latitude);
            ps.setBigDecimal(paramIndex++, radius.multiply(new BigDecimal(1000))); // 미터 단위로 변환
            
            if (toiletName != null && !toiletName.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + toiletName + "%");
            }
            if (toiletType != null) {
                ps.setString(paramIndex++, toiletType.getName());
            }
            
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, size);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiPublicToiletInfo toilet = setPoiPublicToiletInfo(rs);
                    entityList.add(toilet);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiPublicToiletInfo] 위치 기반 검색 완료 (통합) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] 위치 기반 검색 쿼리 실행 중 오류 발생 (통합)", e);
            throw new RuntimeException("Database location query failed", e);
        }
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }



    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiPublicToiletInfo> findBySidoCodeWithPaging(String sidoCode, int offset, int size) {
        String sql = addCountToQuery(toiletBaseQuery) + "AND sido_code = ? " + toiletOrderBy + " OFFSET ? LIMIT ?";

        log.debug("[PoiPublicToiletInfo] 시도별 조회 쿼리: {}", sql);

        List<PoiPublicToiletInfo> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, sidoCode);
            ps.setInt(2, offset);
            ps.setInt(3, size);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiPublicToiletInfo toilet = setPoiPublicToiletInfo(rs);
                    entityList.add(toilet);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiPublicToiletInfo] 시도별 조회 완료 - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] 시도별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database sido query failed", e);
        }
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }



    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiPublicToiletInfo> findAllWithPaging(int offset, int size) {
        String sql = addCountToQuery(toiletBaseQuery) + toiletOrderBy + " OFFSET " + offset + " LIMIT " + size;

        log.debug("[PoiPublicToiletInfo] 전체 조회 쿼리 (페이징): {}", sql);

        List<PoiPublicToiletInfo> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PoiPublicToiletInfo toilet = setPoiPublicToiletInfo(rs);
                entityList.add(toilet);
                // 첫 번째 행에서 total_count 가져오기
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
            }
            log.debug("[PoiPublicToiletInfo] 전체 조회 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] 전체 조회 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database all query failed", e);
        }
        
        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }



    @Override
    public List<PoiPublicToiletInfo> findAllToilets() {
        String sql = toiletBaseQuery + toiletOrderBy;

        log.debug("[PoiPublicToiletInfo] 전체 조회 쿼리: {}", sql);

        List<PoiPublicToiletInfo> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PoiPublicToiletInfo toilet = setPoiPublicToiletInfo(rs);
                entityList.add(toilet);
            }
            log.debug("[PoiPublicToiletInfo] 전체 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] 전체 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database findAll query failed", e);
        }
        
        return entityList;
    }

    @Override
    public List<PoiPublicToiletInfo> findByCategoryConditions(String toiletName, String sidoCode, PoiPublicToiletType toiletType, 
                                                             String open24hYn) {
        StringBuilder sql = new StringBuilder(toiletBaseQuery);
        sql = buildCategoryConditionsSql(sql, toiletName, sidoCode, toiletType, open24hYn);
        sql.append(toiletOrderBy);

        log.debug("[PoiPublicToiletInfo] 카테고리 검색 쿼리 (비페이징): {}", sql);

        List<PoiPublicToiletInfo> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {
            
            while (rs.next()) {
                PoiPublicToiletInfo toilet = setPoiPublicToiletInfo(rs);
                entityList.add(toilet);
            }
            log.debug("[PoiPublicToiletInfo] 카테고리 검색 완료 (비페이징) - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] 카테고리 검색 쿼리 실행 중 오류 발생 (비페이징)", e);
            throw new RuntimeException("Database category search query failed", e);
        }
        
        return entityList;
    }

    @Override
    public List<PoiPublicToiletInfo> findByLocation(BigDecimal latitude, BigDecimal longitude, BigDecimal radius) {
        // 단일 함수로 통합 - findByLocationWithConditions를 null 조건으로 호출
        return findByLocationWithConditions(latitude, longitude, radius, null, null, null);
    }

    @Override
    public List<PoiPublicToiletInfo> findByLocationWithConditions(BigDecimal latitude, BigDecimal longitude, 
                                                                 BigDecimal radius, String toiletName, 
                                                                 PoiPublicToiletType toiletType, String open24hYn) {
        StringBuilder sql = new StringBuilder(toiletBaseQueryWithDistance);
        sql.append("AND ST_Distance_Sphere(POINT(longitude, latitude), POINT(?, ?)) <= ? ");
        
        // 추가 검색 조건 처리
        if (toiletName != null && !toiletName.trim().isEmpty()) {
            sql.append("AND toilet_name LIKE ? ");
        }
        if (toiletType != null) {
            sql.append("AND toilet_type = ? ");
        }
        if ("Y".equals(open24hYn)) {
            sql.append("AND open_time LIKE '%24시간%' ");
        }
        
        sql.append(toiletOrderByDistance);

        log.debug("[PoiPublicToiletInfo] 위치 기반 검색 쿼리 (통합): {}", sql);

        List<PoiPublicToiletInfo> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            ps.setBigDecimal(paramIndex++, longitude);
            ps.setBigDecimal(paramIndex++, latitude);
            ps.setBigDecimal(paramIndex++, radius);
            
            if (toiletName != null && !toiletName.trim().isEmpty()) {
                ps.setString(paramIndex++, "%" + toiletName + "%");
            }
            if (toiletType != null) {
                ps.setString(paramIndex++, toiletType.getName());
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiPublicToiletInfo toilet = setPoiPublicToiletInfo(rs);
                    entityList.add(toilet);
                }
            }
            log.debug("[PoiPublicToiletInfo] 위치 기반 검색 완료 (통합) - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] 위치 기반 검색 쿼리 실행 중 오류 발생 (통합)", e);
            throw new RuntimeException("Database location search query failed", e);
        }
        
        return entityList;
    }


// 페이징 결과와 총 개수를 함께 반환하는 메서드들

    @Override
    public com.sweetk.iitp.api.dto.internal.MvPoiPageResult<PoiPublicToiletInfo> findByCategoryConditionsWithPaging(
            String toiletName, String sidoCode, PoiPublicToiletType toiletType,
            String open24hYn, int offset, int size) {
        StringBuilder sql = new StringBuilder(addCountToQuery(toiletBaseQuery));
        sql = buildCategoryConditionsSql(sql, toiletName, sidoCode, toiletType, open24hYn);
        sql.append(toiletOrderBy).append(" OFFSET ").append(offset).append(" LIMIT ").append(size);

        log.debug("[PoiPublicToiletInfo] 카테고리 검색 쿼리 (페이징 + 카운트): {}", sql);

        List<PoiPublicToiletInfo> entityList = new ArrayList<>();
        long totalCount = 0;

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql.toString())) {

            while (rs.next()) {
                PoiPublicToiletInfo toilet = setPoiPublicToiletInfo(rs);
                entityList.add(toilet);
                // 첫 번째 행에서 total_count 가져오기
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
            }
            log.debug("[PoiPublicToiletInfo] 카테고리 검색 완료 (페이징 + 카운트) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiPublicToiletInfo] 카테고리 검색 쿼리 실행 중 오류 발생 (페이징 + 카운트)", e);
            throw new RuntimeException("Database category search query failed", e);
        }

        return new com.sweetk.iitp.api.dto.internal.MvPoiPageResult<>(entityList, totalCount);
    }

    



} 