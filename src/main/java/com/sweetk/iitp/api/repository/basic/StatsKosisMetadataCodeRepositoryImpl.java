package com.sweetk.iitp.api.repository.basic;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.constant.SysConstants;
import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.QStatsKosisMetadataCodeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class StatsKosisMetadataCodeRepositoryImpl implements StatsKosisMetadataCodeRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QStatsKosisMetadataCodeEntity qMetaCode = QStatsKosisMetadataCodeEntity.statsKosisMetadataCodeEntity;

    @Override
    public List<StatMetaCodeDB> findItemInfoByCatObj(Integer srcDataId, LocalDate statLatestChnDt) {
        return queryFactory
                .select(Projections.constructor(StatMetaCodeDB.class,
                        qMetaCode.itmId,
                        qMetaCode.itmNm,
                        qMetaCode.objId,
                        qMetaCode.objNm))
                .from(qMetaCode)
                .where(
                        qMetaCode.srcDataId.eq(srcDataId),
                        qMetaCode.statLatestChnDt.eq(statLatestChnDt),
                        qMetaCode.objNm.ne(SysConstants.Stats.KOSIS_META_ITEM_OBJ_NAME)
                )
                .fetch();
    }

    @Override
    public List<StatMetaCodeDB> findItemInfoByItemObj(Integer srcDataId, LocalDate statLatestChnDt) {
        return queryFactory
                .select(Projections.constructor(StatMetaCodeDB.class,
                        qMetaCode.itmId,
                        qMetaCode.itmNm,
                        qMetaCode.objId,
                        qMetaCode.objNm))
                .from(qMetaCode)
                .where(
                        qMetaCode.srcDataId.eq(srcDataId),
                        qMetaCode.statLatestChnDt.eq(statLatestChnDt),
                        qMetaCode.objNm.eq(SysConstants.Stats.KOSIS_META_ITEM_OBJ_NAME)
                )
                .fetch();
    }

    @Override
    public List<StatMetaCodeDB> findMetaCodesByIds(Integer srcDataId, LocalDate statLatestChnDt, Set<String> itemIds) {
        return queryFactory
                .select(Projections.constructor(StatMetaCodeDB.class,
                        qMetaCode.itmId,
                        qMetaCode.itmNm,
                        qMetaCode.objNm))
                .from(qMetaCode)
                .where(
                        qMetaCode.srcDataId.eq(srcDataId),
                        qMetaCode.statLatestChnDt.eq(statLatestChnDt),
                        qMetaCode.itmId.in(itemIds)
                )
                .fetch();
    }
}
