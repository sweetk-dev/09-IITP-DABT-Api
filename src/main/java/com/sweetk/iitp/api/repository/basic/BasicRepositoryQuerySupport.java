package com.sweetk.iitp.api.repository.basic;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BasicRepositoryQuerySupport {

    @Autowired
    protected JPAQueryFactory queryFactory;

    // 최근 통계 테이터 조회 쿼리
    protected JPAQuery<StatDataItemDB> buildLatestStatDataItemQuery(
            QBaseStatsEntity stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer fromYear) {

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
                .where(
                        stats.prdDe.goe(fromYear)
                                .and(stats.statLatestChnDt.eq(srcDataInfo.getStatLatestChnDt()))
                )
                .orderBy(stats.prdDe.asc());
    }


    // 특정 년도 통계 테이터 조회 쿼리
    protected JPAQuery<StatDataItemDB> buildTargetStatDataItemQuery(
            QBaseStatsEntity stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer targetYear) {

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
                .where(
                        stats.prdDe.eq(targetYear)
                                .and(stats.statLatestChnDt.eq(srcDataInfo.getStatLatestChnDt()))
                )
                .orderBy(stats.prdDe.asc());
    }
}
