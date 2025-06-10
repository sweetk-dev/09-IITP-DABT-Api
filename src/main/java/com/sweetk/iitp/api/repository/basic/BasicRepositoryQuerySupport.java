package com.sweetk.iitp.api.repository.basic;


import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.EntityPathBase;
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
    protected <T extends BaseStatsEntity, Q extends EntityPathBase<T>> JPAQuery<StatDataItemDB> buildLatestStatDataItemQuery(
            Q stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer fromYear) {

        return queryFactory
                .select(Projections.constructor(StatDataItemDB.class,
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName())
                ))
                .from(stats)
                .where(
                        Expressions.booleanTemplate("prd_de >= {0}", fromYear)
                                .and(Expressions.booleanTemplate("src_latest_chn_dt = {0}", srcDataInfo.getStatLatestChnDt()))
                )
                .orderBy(Expressions.stringTemplate("prd_de").asc());
    }


    // 특정 년도 통계 테이터 조회 쿼리
    protected <T extends BaseStatsEntity, Q extends EntityPathBase<T>> JPAQuery<StatDataItemDB> buildTargetStatDataItemQuery(
            Q stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer targetYear) {

        return queryFactory
                .select(Projections.constructor(StatDataItemDB.class,
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName()),
                        Expressions.constant(stats.getMetadata().getName())
                ))
                .from(stats)
                .where(
                        Expressions.booleanTemplate("prd_de = {0}", targetYear)
                                .and(Expressions.booleanTemplate("src_latest_chn_dt = {0}", srcDataInfo.getStatLatestChnDt()))
                )
                .orderBy(Expressions.stringTemplate("prd_de").asc());
    }
}
