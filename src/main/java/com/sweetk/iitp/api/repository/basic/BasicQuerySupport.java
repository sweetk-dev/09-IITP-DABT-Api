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
import java.time.LocalDate;
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
        com.querydsl.core.types.dsl.NumberPath<Short> prdDePath = path.getNumber("prdDe", Short.class);

        Expression<String> dtExpr = path.getType().isAssignableFrom(BaseStatsDtStringEntity.class)
                ? path.getString("dt")
                : path.getNumber("dt", BigDecimal.class).stringValue();


        return queryFactory
                .select(constructor(StatDataItemDB.class,
                        path.get("prdDe", Short.class),
                        path.get("c1", String.class),
                        path.get("c2", String.class),
                        path.get("c3", String.class),
                        path.get("c1ObjNm", String.class),
                        path.get("c2ObjNm", String.class),
                        path.get("c3ObjNm", String.class),
                        path.get("itmId", String.class),
                        path.get("unitNm", String.class),
                        dtExpr,
                        path.get("lstChnDe", java.time.LocalDate.class),
                        path.get("srcDataId", Integer.class)
                ))
                .from(stats)
                .where(
                        prdDePath.goe(fromYear.shortValue()),
                        path.getDate("srcLatestChnDt", java.util.Date.class)
                                .eq(java.sql.Date.valueOf(srcDataInfo.getStatLatestChnDt()))
                )
                .orderBy(prdDePath.asc())
                .fetch();
    }




    public List<StatDataItemDB> findTargetStats(
            EntityPath<T> stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer fromYear
    ) {
        PathBuilder<T> path = new PathBuilder<>(stats.getType(), stats.getMetadata());
        com.querydsl.core.types.dsl.NumberPath<Short> prdDePath = path.getNumber("prdDe", Short.class);

        LocalDate statLatestChnDt = LocalDate.parse(srcDataInfo.getStatLatestChnDt());

        Expression<String> dtExpr = path.getType().isAssignableFrom(BaseStatsDtStringEntity.class)
                ? path.getString("dt")
                : path.getNumber("dt", BigDecimal.class).stringValue();

        return queryFactory
                .select(constructor(StatDataItemDB.class,
                        path.get("prdDe", Short.class),
                        path.get("c1", String.class),
                        path.get("c2", String.class),
                        path.get("c3", String.class),
                        path.get("c1ObjNm", String.class),
                        path.get("c2ObjNm", String.class),
                        path.get("c3ObjNm", String.class),
                        path.get("itmId", String.class),
                        path.get("unitNm", String.class),
                        dtExpr,
                        path.get("lstChnDe", java.time.LocalDate.class),
                        path.get("srcDataId", Integer.class)
                ))
                .from(stats)
                .where(
                        prdDePath.eq(fromYear.shortValue()),
                        path.getDate("srcLatestChnDt", java.util.Date.class)
                                .eq(statLatestChnDt)
                )
                .orderBy(prdDePath.asc())
                .fetch();
    }
}
