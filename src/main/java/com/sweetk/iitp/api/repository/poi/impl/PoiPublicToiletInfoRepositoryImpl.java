package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.config.DistanceCalculationConfig;
import com.sweetk.iitp.api.constant.poi.PoiPublicToiletType;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.dto.poi.PoiPublicToilet;
import com.sweetk.iitp.api.dto.poi.PoiPublicToiletLocation;
import com.sweetk.iitp.api.util.RepositoryUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
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
public class PoiPublicToiletInfoRepositoryImpl implements PoiPublicToiletInfoRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    private final DistanceCalculationConfig distanceConfig;

    // 공통 컬럼 정의
    private static final String TOILET_COMMON_COLUMNS = 
            "toilet_id, sido_code, toilet_name, toilet_type, basis, addr_road, addr_jibun, " +
            "m_toilet_count, m_urinal_count, m_dis_toilet_count, m_dis_urinal_count, m_child_toilet_count, m_child_urinal_count, " +
            "f_toilet_count, f_dis_toilet_count, f_child_toilet_count, " +
            "managing_org, phone_number, open_time, open_time_detail, install_dt, " +
            "latitude, longitude, owner_type, waste_process_type, safety_target_yn, " +
            "emg_bell_yn, emg_bell_location, cctv_yn, diaper_table_yn, diaper_table_location, " +
            "remodeled_dt, base_dt";

    private static final String TOILET_TOT_CNT_COLUMN = " COUNT(*) OVER() AS total_count ";
    private static final String TOILET_BASE_FROM = " FROM poi_public_toilet_info WHERE del_yn = 'N' ";


    private static final String TOILET_ORDER_BY_NAME = " ORDER BY toilet_name";
    private static final String TOILET_ORDER_BY_DISTANCE = " ORDER BY distance";

    private static final String TOILET_QUERY = "SELECT " + TOILET_COMMON_COLUMNS + " " +
                                                TOILET_BASE_FROM;

    private static final String TOILET_QUERY_WITH_COUNT = "SELECT " + TOILET_COMMON_COLUMNS + ", " +
                                                        TOILET_TOT_CNT_COLUMN  +
                                                        TOILET_BASE_FROM;

    private final String TOILET_LOCATION_QUERY;
    private final String TOILET_LOCATION_QUERY_WITH_COUNT;


    public PoiPublicToiletInfoRepositoryImpl(DistanceCalculationConfig distanceConfig) {
        this.distanceConfig = distanceConfig;
        this.TOILET_LOCATION_QUERY = "SELECT " + TOILET_COMMON_COLUMNS + ", " +
                                    distanceConfig.getDistanceCalculationSql() + " AS distance " +
                                    TOILET_BASE_FROM;

        this.TOILET_LOCATION_QUERY_WITH_COUNT = "SELECT " + TOILET_COMMON_COLUMNS + ", " +
                                                distanceConfig.getDistanceCalculationSql() + " AS distance, " +
                                                TOILET_TOT_CNT_COLUMN +
                                                TOILET_BASE_FROM;

    }



    /*******************************
     ** 공중 화장실 ID로 조회
     *******************************/

    @Override
    public Optional<PoiPublicToilet> findByIdToDto(Integer toiletId) {
        StringBuilder sql = new StringBuilder(TOILET_QUERY);
        sql.append("AND toilet_id = ").append(toiletId).append(" ");

        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString());
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return Optional.of(setPoiPublicToiletInfo(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            log.error("공중 화장실 ID 조회 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("공중 화장실 조회 실패", e);
        }
    }



    /*******************************
     ** 전체 공중 화장실 조회
     *******************************/
    //전체 공중 화장실 조회 (전체 결과)
    @Override
    public List<PoiPublicToilet> findAllToilets() {
        String sql = TOILET_QUERY + TOILET_ORDER_BY_NAME;

        log.debug("[PoiPublicToilet] 전체 조회 쿼리: {}", sql);

        List<PoiPublicToilet> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PoiPublicToilet toilet = setPoiPublicToiletInfo(rs);
                entityList.add(toilet);
            }
            log.debug("[PoiPublicToilet] 전체 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiPublicToilet] 전체 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database findAll query failed", e);
        }

        return entityList;
    }

    // 전체 공중 화장실 조회 (페이징)
    @Override
    public PoiPageResult<PoiPublicToilet> findAllWithPagingCount(int offset, int size) {
        StringBuilder sql = new StringBuilder(TOILET_QUERY_WITH_COUNT).append(TOILET_ORDER_BY_NAME);
        sql = RepositoryUtils.addQueryOffset(sql,offset, size);

        log.debug("[PoiPublicToilet] 전체 조회 쿼리 (페이징): {}", sql);

        List<PoiPublicToilet> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PoiPublicToilet toilet = setPoiPublicToiletInfo(rs);
                entityList.add(toilet);
                // 첫 번째 행에서 total_count 가져오기
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
            }
            log.debug("[PoiPublicToilet] 전체 조회 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiPublicToilet] 전체 조회 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database all query failed", e);
        }

        return new PoiPageResult<>(entityList, totalCount);
    }



    /*******************************
     ** 시도별 공중 화장실 조회
     *******************************/
    //시도 코드로 공중 화장실 조회 (DTO 반환)
    @Override
    public List<PoiPublicToilet> findBySidoCodeToDto(String sidoCode) {
        StringBuilder sql = new StringBuilder(TOILET_QUERY)
                .append("AND sido_code = ? ")
                .append(TOILET_ORDER_BY_NAME);

        log.debug("[PoiPublicToilet] 시도별 조회 쿼리: {}", sql);

        List<PoiPublicToilet> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            ps.setString(1, sidoCode);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiPublicToilet toilet = setPoiPublicToiletInfo(rs);
                    entityList.add(toilet);
                }
            }
            log.debug("[PoiPublicToilet] 시도별 조회 완료 - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiPublicToilet] 시도별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database sido query failed", e);
        }
        
        return entityList;
    }

    //시도 코드로 공중 화장실 조회 (페이징)
    @Override
    public PoiPageResult<PoiPublicToilet> findBySidoCodeWithPagingCount(String sidoCode, int offset, int size) {
        StringBuilder sql = new StringBuilder(TOILET_QUERY_WITH_COUNT)
                .append("AND sido_code = ? ")
                .append(TOILET_ORDER_BY_NAME);
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);

        log.debug("[PoiPublicToilet] 시도별 조회 쿼리: {}", sql);

        List<PoiPublicToilet> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setString(1, sidoCode);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiPublicToilet toilet = setPoiPublicToiletInfo(rs);
                    entityList.add(toilet);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiPublicToilet] 시도별 조회 완료 - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiPublicToilet] 시도별 조회 쿼리 실행 중 오류 발생", e);
            throw new RuntimeException("Database sido query failed", e);
        }

        return new PoiPageResult<>(entityList, totalCount);
    }



    /*******************************
     **  카테고리 기반 검색 공중 화장실 조회
     *******************************/
    //카테고리 조건으로 공중 화장실 검색 (전체 결과)
    @Override
    public List<PoiPublicToilet> findByCategoryConditions(String toiletName, String sidoCode, PoiPublicToiletType toiletType,
                                                          String open24hYn) {
        StringBuilder sql = new StringBuilder(TOILET_QUERY);
        sql = buildCategoryConditionsSql(sql, toiletName, sidoCode, toiletType, open24hYn);
        sql.append(TOILET_ORDER_BY_NAME);

        log.debug("[PoiPublicToilet] 카테고리 검색 쿼리 (비페이징): {}", sql);

        List<PoiPublicToilet> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PoiPublicToilet toilet = setPoiPublicToiletInfo(rs);
                entityList.add(toilet);
            }
            log.debug("[PoiPublicToilet] 카테고리 검색 완료 (비페이징) - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiPublicToilet] 카테고리 검색 쿼리 실행 중 오류 발생 (비페이징)", e);
            throw new RuntimeException("Database category search query failed", e);
        }

        return entityList;
    }

    //카테고리 조건으로 공중 화장실 검색 (페이징 + 총 개수)
    @Override
    public PoiPageResult<PoiPublicToilet> findByCategoryConditionsWithPagingCount(
            String toiletName, String sidoCode, PoiPublicToiletType toiletType,
            String open24hYn, int offset, int size) {

        StringBuilder sql = new StringBuilder(TOILET_QUERY_WITH_COUNT);
        sql = buildCategoryConditionsSql(sql, toiletName, sidoCode, toiletType, open24hYn);
        sql.append(TOILET_ORDER_BY_NAME);
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);

        log.debug("[PoiPublicToilet] 카테고리 검색 쿼리 (페이징 + 카운트): {}", sql);

        List<PoiPublicToilet> entityList = new ArrayList<>();
        long totalCount = 0;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PoiPublicToilet toilet = setPoiPublicToiletInfo(rs);
                entityList.add(toilet);
                // 첫 번째 행에서 total_count 가져오기
                if (totalCount == 0) {
                    totalCount = rs.getLong("total_count");
                }
            }
            log.debug("[PoiPublicToilet] 카테고리 검색 완료 (페이징 + 카운트) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiPublicToilet] 카테고리 검색 쿼리 실행 중 오류 발생 (페이징 + 카운트)", e);
            throw new RuntimeException("Database category search query failed", e);
        }

        return new PoiPageResult<>(entityList, totalCount);
    }



    /*******************************
     **  거리 정보 포함 위치 기반 검색 공중 화장실 조회
     *******************************/
    @Override
    public List<PoiPublicToiletLocation> findByLocationWithConditions(BigDecimal latitude, BigDecimal longitude,
                                                                                 BigDecimal radius, String toiletName,
                                                                                 PoiPublicToiletType toiletType, String open24hYn) {
        StringBuilder sql = new StringBuilder(TOILET_LOCATION_QUERY);
        sql = buildCategoryConditionsSql(sql, toiletName, null, toiletType, open24hYn);
        sql.append(distanceConfig.getDistanceFilterSql(latitude, longitude, radius.multiply(new BigDecimal(1000))));
        sql.append(TOILET_ORDER_BY_DISTANCE);

        log.debug("[PoiPublicToilet] 거리 정보 포함 위치 기반 검색 쿼리 (조건 포함): {}", sql);

        List<PoiPublicToiletLocation> entityList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // 거리 계산을 위한 파라미터 설정 (longitude, latitude)
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiPublicToiletLocation toilet = setPoiPublicToiletInfoLocation(rs);
                    entityList.add(toilet);
                }
            }
            log.debug("[PoiPublicToilet] 거리 정보 포함 위치 기반 검색 완료 (조건 포함) - 결과 개수: {}", entityList.size());
        } catch (SQLException e) {
            log.error("[PoiPublicToilet] 거리 정보 포함 위치 기반 검색 쿼리 실행 중 오류 발생 (조건 포함)", e);
            throw new RuntimeException("Database location with distance and conditions query failed", e);
        }
        
        return entityList;
    }

    @Override
    public PoiPageResult<PoiPublicToiletLocation> findByLocationWithConditionsAndPagingCount(BigDecimal latitude, BigDecimal longitude,
                                                                                           BigDecimal radius, String toiletName,
                                                                                           PoiPublicToiletType toiletType, String open24hYn,
                                                                                           int offset, int size) {
        StringBuilder sql = new StringBuilder(TOILET_LOCATION_QUERY_WITH_COUNT);
        sql = buildCategoryConditionsSql(sql, toiletName, null, toiletType, open24hYn);
        sql.append(distanceConfig.getDistanceFilterSql(latitude, longitude, radius.multiply(new BigDecimal(1000))));
        sql.append(TOILET_ORDER_BY_DISTANCE);
        sql = RepositoryUtils.addQueryOffset(sql, offset, size);

        log.debug("[PoiPublicToilet] 거리 정보 포함 위치 기반 검색 쿼리 (페이징): {}", sql);

        List<PoiPublicToiletLocation> entityList = new ArrayList<>();
        long totalCount = 0;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // 거리 계산을 위한 파라미터 설정 (longitude, latitude)
            ps.setBigDecimal(1, longitude);
            ps.setBigDecimal(2, latitude);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PoiPublicToiletLocation toilet = setPoiPublicToiletInfoLocation(rs);
                    entityList.add(toilet);
                    // 첫 번째 행에서 total_count 가져오기
                    if (totalCount == 0) {
                        totalCount = rs.getLong("total_count");
                    }
                }
            }
            log.debug("[PoiPublicToilet] 거리 정보 포함 위치 기반 검색 완료 (페이징) - 결과 개수: {}, 총 개수: {}", entityList.size(), totalCount);
        } catch (SQLException e) {
            log.error("[PoiPublicToilet] 거리 정보 포함 위치 기반 검색 쿼리 실행 중 오류 발생 (페이징)", e);
            throw new RuntimeException("Database location with distance and paging query failed", e);
        }
        
        return new PoiPageResult<>(entityList, totalCount);
    }


    // Helper method to create PoiPublicToilet from ResultSet
    private PoiPublicToilet setPoiPublicToiletInfo(ResultSet rs) throws SQLException {
        return new PoiPublicToilet(
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

    // Helper method to create PoiPublicToiletLocation from ResultSet
    private PoiPublicToiletLocation setPoiPublicToiletInfoLocation(ResultSet rs) throws SQLException {
        // 직접 Location 객체 생성 (메모리 효율성)
        return new PoiPublicToiletLocation(
                rs.getInt("toilet_id"),
                rs.getString("sido_code"),
                rs.getString("toilet_name"),
                rs.getString("toilet_type"),
                rs.getString("basis"),
                rs.getString("addr_road"),
                rs.getString("addr_jibun"),
                rs.getObject("m_toilet_count", Integer.class),
                rs.getObject("m_urinal_count", Integer.class),
                rs.getObject("m_dis_toilet_count", Integer.class),
                rs.getObject("m_dis_urinal_count", Integer.class),
                rs.getObject("m_child_toilet_count", Integer.class),
                rs.getObject("m_child_urinal_count", Integer.class),
                rs.getObject("f_toilet_count", Integer.class),
                rs.getObject("f_dis_toilet_count", Integer.class),
                rs.getObject("f_child_toilet_count", Integer.class),
                rs.getString("managing_org"),
                rs.getString("phone_number"),
                rs.getString("open_time"),
                rs.getString("open_time_detail"),
                rs.getString("install_dt"),
                rs.getObject("latitude", Double.class),
                rs.getObject("longitude", Double.class),
                rs.getString("owner_type"),
                rs.getString("waste_process_type"),
                rs.getString("safety_target_yn"),
                rs.getString("emg_bell_yn"),
                rs.getString("emg_bell_location"),
                rs.getString("cctv_yn"),
                rs.getString("diaper_table_yn"),
                rs.getString("diaper_table_location"),
                rs.getString("remodeled_dt"),
                rs.getDate("base_dt") != null ? rs.getDate("base_dt").toLocalDate() : null,
                rs.getInt("distance")
        );
    }

    // Helper method to build category conditions SQL
    private StringBuilder buildCategoryConditionsSql(StringBuilder sql, String toiletName, String sidoCode,
                                                     PoiPublicToiletType toiletType, String open24hYn) {
        // 인덱스 활용을 위한 조건 순서 최적화
        if (sidoCode != null && !sidoCode.trim().isEmpty()) {
            sql.append("AND sido_code = '").append(RepositoryUtils.escapeSql(sidoCode)).append("' ");
        }
        if (toiletType != null) {
            sql.append("AND toilet_type = '").append(RepositoryUtils.escapeSql(toiletType.getName())).append("' ");
        }
        if (toiletName != null && !toiletName.trim().isEmpty()) {
            sql.append("AND toilet_name LIKE '%").append(RepositoryUtils.escapeSql(toiletName)).append("%' ");
        }
        if ("Y".equals(open24hYn)) {
            sql.append("AND open_time LIKE '%24시간%' ");
        }

        return sql;
    }
} 