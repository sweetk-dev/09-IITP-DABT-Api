package com.sweetk.iitp.api.repository.basic.house;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.housing.QStatsDisRegNatlByNewEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisRegNatlByNewRepositoryImpl implements StatsDisRegNatlByNewRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<StatDataItemDB> findLatestRegNewData(Integer fromYear) {
        QStatsDisRegNatlByNewEntity stats = QStatsDisRegNatlByNewEntity.statsDisRegNatlByNewEntity;

        return queryFactory
                .select(Projections.constructor(StatDataItemDB.class,
                        stats.prdDe,
                        stats.c1,
                        stats.c2,
                        stats.c3,
                        stats.c1ObjNm,
                        stats.c2ObjNm,
                        stats.c3ObjNm,
                        stats.itmId,
                        stats.unitNm,
                        stats.dt.stringValue(),
                        stats.lstChnDe,
                        stats.srcDataId
                ))
                .from(stats)
                .where(stats.prdDe.goe(fromYear))
                .orderBy(stats.prdDe.asc())
                .fetch();
    }
} 