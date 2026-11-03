package com.sweetk.iitp.api.repository.basic.emotion;

import com.sweetk.iitp.api.constant.basic.EmotionStatType;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 기초-정서 자립 현황 통계 조회.
 * <p>
 * 20개 통합 테이블(stats_dis_emo_*)은 모두 같은 칸 구성이라 엔티티를 따로 두지 않고 하나의 조회로 읽는다.
 * 테이블 이름은 {@link EmotionStatType} 에 정해 둔 값만 쓴다(요청 값을 SQL 에 넣지 않는다).
 * 조회 조건·결과 형식은 다른 기초 통계(BasicQuerySupport)와 같다.
 */
@Repository
public class EmotionStatsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SELECT_COLUMNS =
            "SELECT prd_de, c1, c2, c3, itm_id, unit_nm, dt, lst_chn_de, src_data_id FROM %s ";

    /** 조회기간(fromYear~toYear)의 최신 자료갱신분 */
    public List<StatDataItemDB> findLatest(EmotionStatType type, StatsSrcDataInfoEntity srcDataInfo,
                                           Integer fromYear, Integer toYear) {
        String sql = String.format(SELECT_COLUMNS
                + "WHERE src_data_id = ? AND prd_de >= ? AND prd_de <= ? AND src_latest_chn_dt = ? "
                + "ORDER BY prd_de ASC, id ASC", type.getIntgTblId());
        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(sql)
                .setParameter(1, srcDataInfo.getSrcDataId())
                .setParameter(2, fromYear.shortValue())
                .setParameter(3, toYear.shortValue())
                .setParameter(4, LocalDate.parse(srcDataInfo.getStatLatestChnDt()))
                .getResultList();
        return toItems(rows);
    }

    /** 해당 연도의 최신 자료갱신분 */
    public List<StatDataItemDB> findByYear(EmotionStatType type, StatsSrcDataInfoEntity srcDataInfo,
                                           Integer targetYear) {
        String sql = String.format(SELECT_COLUMNS
                + "WHERE src_data_id = ? AND prd_de = ? AND src_latest_chn_dt = ? "
                + "ORDER BY id ASC", type.getIntgTblId());
        @SuppressWarnings("unchecked")
        List<Object[]> rows = entityManager.createNativeQuery(sql)
                .setParameter(1, srcDataInfo.getSrcDataId())
                .setParameter(2, targetYear.shortValue())
                .setParameter(3, LocalDate.parse(srcDataInfo.getStatLatestChnDt()))
                .getResultList();
        return toItems(rows);
    }

    private List<StatDataItemDB> toItems(List<Object[]> rows) {
        return rows.stream()
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
}
