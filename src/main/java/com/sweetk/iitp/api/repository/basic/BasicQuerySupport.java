package com.sweetk.iitp.api.repository.basic;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.BaseStatsDtStringEntity;
import com.sweetk.iitp.api.entity.basic.StatsCommon;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
public abstract class BasicQuerySupport<T extends StatsCommon> {

    protected final JPAQueryFactory queryFactory;


    // 공통 쿼리 메서드
    public List<StatDataItemDB> findLatestStats(
            EntityPath<T> stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer fromYear
    ) {
        PathBuilder<T> path = new PathBuilder<>(stats.getType(), stats.getMetadata());

        Expression<String> prdDeExpr = path.getNumber("prdDe", Short.class).stringValue();
        Expression<String> dtExpr = path.getType().isAssignableFrom(BaseStatsDtStringEntity.class)
                ? path.getString("dt")
                : path.getNumber("dt", BigDecimal.class).stringValue();

        return queryFactory
                .select(constructor(StatDataItemDB.class,
                        prdDeExpr,
                        path.getString("c1"),
                        path.getString("c2"),
                        path.getString("c3"),
                        path.getString("c1ObjNm"),
                        path.getString("c2ObjNm"),
                        path.getString("c3ObjNm"),
                        path.getString("itmId"),
                        path.getString("unitNm"),
                        dtExpr,
                        path.getString("lstChnDe"),
                        path.getString("srcDataId")
                ))
                .from(stats)
                .where(
                        path.getString("prdDe").goe(String.valueOf(fromYear)),
                        path.getDate("statLatestChnDt", java.util.Date.class)
                                .eq(java.sql.Date.valueOf(srcDataInfo.getStatLatestChnDt()))
                )
                .orderBy(path.getString("prdDe").asc())
                .fetch();
    }




    public List<StatDataItemDB> findTargetStats(
            EntityPath<T> stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer fromYear
    ) {
        PathBuilder<T> path = new PathBuilder<>(stats.getType(), stats.getMetadata());

        Expression<String> prdDeExpr = path.getNumber("prdDe", Short.class).stringValue();
        Expression<String> dtExpr = path.getType().isAssignableFrom(BaseStatsDtStringEntity.class)
                ? path.getString("dt")
                : path.getNumber("dt", BigDecimal.class).stringValue();

        return queryFactory
                .select(constructor(StatDataItemDB.class,
                        prdDeExpr,
                        path.getString("c1"),
                        path.getString("c2"),
                        path.getString("c3"),
                        path.getString("c1ObjNm"),
                        path.getString("c2ObjNm"),
                        path.getString("c3ObjNm"),
                        path.getString("itmId"),
                        path.getString("unitNm"),
                        dtExpr,
                        path.getString("lstChnDe"),
                        path.getString("srcDataId")
                ))
                .from(stats)
                .where(
                        path.getString("prdDe").eq(String.valueOf(fromYear)),
                        path.getDate("statLatestChnDt", java.util.Date.class)
                                .eq(java.sql.Date.valueOf(srcDataInfo.getStatLatestChnDt()))
                )
                .orderBy(path.getString("prdDe").asc())
                .fetch();
    }
}
