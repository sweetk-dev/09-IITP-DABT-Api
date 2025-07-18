package com.sweetk.iitp.api.repository.basic;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsCommon;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class BasicQuerySupport<T extends StatsCommon> {

    protected final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    // DB 컬럼명 상수
    private static final String PRD_DE_COLUMN = "prd_de";
    private static final String SRC_LATEST_CHN_DT_COLUMN = "src_latest_chn_dt";


    /**
     *  조회기간에 맞는 통계 데이터 의 총 개수 조회
     * @param stats
     * @param srcDataInfo
     * @param fromYear
     * @param toYear
     * @return
     */
    public Integer  getLatestStatsCount (
            EntityPath<T> stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer fromYear,
            Integer toYear
    ){
        String tableName = getTableName(stats.getType());
        LocalDate statLatestChnDt = LocalDate.parse(srcDataInfo.getStatLatestChnDt());

        String sql = String.format(
                "SELECT count(*) " +
                        "FROM %s " +
                        "WHERE %s >= ? AND %s <= ? AND %s = ? " +
                        //"WHERE %s >= ? AND %s <= ? AND %s = ? AND deleted_at IS NULL "
                tableName, PRD_DE_COLUMN, PRD_DE_COLUMN, SRC_LATEST_CHN_DT_COLUMN
        );

        Object results = entityManager.createNativeQuery(sql)
                .setParameter(1, fromYear.shortValue())
                .setParameter(2, toYear.shortValue())
                .setParameter(3, statLatestChnDt)
                .getSingleResult();

        return results != null ? ((Number) results).intValue() : 0;
    }



    /**
     *  조회기간에 맞는 통계 데이터 조회  
     *
     * @param stats
     * @param srcDataInfo
     * @param fromYear
     * @param toYear
     * @return List<StatDataItemDB>
     */
    public List<StatDataItemDB> findLatestStats(
            EntityPath<T> stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer fromYear,
            Integer toYear
    ) {

        String tableName = getTableName(stats.getType());
        LocalDate statLatestChnDt = LocalDate.parse(srcDataInfo.getStatLatestChnDt());

        String sql = String.format(
                "SELECT prd_de, c1, c2, c3, itm_id, unit_nm, dt, lst_chn_de, src_data_id " +
                        "FROM %s " +
                        "WHERE %s >= ? AND %s <= ? AND %s = ? " +
                        //"WHERE %s >= ? AND %s <= ? AND %s = ? AND deleted_at IS NULL " +
                        "ORDER BY prd_de ASC",
                tableName, PRD_DE_COLUMN, PRD_DE_COLUMN, SRC_LATEST_CHN_DT_COLUMN
        );

        List<Object[]> results = entityManager.createNativeQuery(sql)
                .setParameter(1, fromYear.shortValue())
                .setParameter(2, toYear.shortValue())
                .setParameter(3, statLatestChnDt)
                .getResultList();

        return results.stream()
                .map(row -> new StatDataItemDB(
                        ((Number) row[0]).shortValue(),
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        (String) row[4],
                        (String) row[5],
                        row[6] != null ? row[6].toString() : null,
                        row[7] != null ? ((Date) row[7]).toLocalDate() : null,
                        ((Number) row[8]).intValue()
                ))
                .collect(Collectors.toList());
    }


    /**
     * 해당 년도의 통계 데이터 조회
     * @param stats
     * @param srcDataInfo
     * @param targetYear
     * @return List<StatDataItemDB>
     */
    public List<StatDataItemDB> findTargetStats(
            EntityPath<T> stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer targetYear
    ) {
        String tableName = getTableName(stats.getType());
        LocalDate statLatestChnDt = LocalDate.parse(srcDataInfo.getStatLatestChnDt());

        String sql = String.format(
                "SELECT prd_de, c1, c2, c3, itm_id, unit_nm, dt, lst_chn_de, src_data_id " +
                        "FROM %s " +
                        "WHERE %s = ? AND %s = ? " +
                        //"WHERE %s = ? AND %s = ? AND deleted_at IS NULL " +
                        "ORDER BY prd_de ASC",
                tableName, PRD_DE_COLUMN, SRC_LATEST_CHN_DT_COLUMN
        );

        List<Object[]> results = entityManager.createNativeQuery(sql)
                .setParameter(1, targetYear.shortValue())
                .setParameter(2, statLatestChnDt)
                .getResultList();

        return results.stream()
                .map(row -> new StatDataItemDB(
                        ((Number) row[0]).shortValue(),
                        (String) row[1],
                        (String) row[2],
                        (String) row[3],
                        (String) row[4],
                        (String) row[5],
                        row[6] != null ? row[6].toString() : null,
                        row[7] != null ? ((Date) row[7]).toLocalDate() : null,
                        ((Number) row[8]).intValue()
                ))
                .collect(Collectors.toList());
    }

    private String getTableName(Class<?> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            throw new IllegalStateException("Entity " + entityClass.getSimpleName() + " must have a @Table annotation.");
        }
        return tableAnnotation.name();
    }
}
