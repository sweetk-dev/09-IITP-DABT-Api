package com.sweetk.iitp.api.repository.basic;


import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.BaseStatsEntity;
import com.sweetk.iitp.api.entity.basic.StatsCommon;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.querydsl.core.types.Projections.constructor;


public abstract class BasicQuerySupport<T extends BaseStatsEntity & StatsCommon> {

    protected final JPAQueryFactory queryFactory;

    protected BasicQuerySupport(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    // 공통 쿼리 메서드
    public List<StatDataItemDB> getLatestStats(
            EntityPath<T> stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer fromYear
    ) {
        PathBuilder<T> path = new PathBuilder<>(stats.getType(), stats.getMetadata());

        return queryFactory
                .select(constructor(StatDataItemDB.class,
                        path.getNumber("prdDe", Short.class),
                        path.getString("c1"),
                        path.getString("c2"),
                        path.getString("c3"),
                        path.getString("c1ObjNm"),
                        path.getString("c2ObjNm"),
                        path.getString("c3ObjNm"),
                        path.getString("itmId"),
                        path.getString("unitNm"),
                        path.getNumber("dt", BigDecimal.class).stringValue(), // BigDecimal → String 변환
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




    public List<StatDataItemDB> getTargetStats(
            EntityPath<T> stats,
            StatsSrcDataInfoEntity srcDataInfo,
            Integer fromYear
    ) {
        PathBuilder<T> path = new PathBuilder<>(stats.getType(), stats.getMetadata());

        return queryFactory
                .select(constructor(StatDataItemDB.class,
                        path.getNumber("prdDe", Short.class),
                        path.getString("c1"),
                        path.getString("c2"),
                        path.getString("c3"),
                        path.getString("c1ObjNm"),
                        path.getString("c2ObjNm"),
                        path.getString("c3ObjNm"),
                        path.getString("itmId"),
                        path.getString("unitNm"),
                        path.getNumber("dt", BigDecimal.class).stringValue(),
                        path.getString("lstChnDe"),
                        path.getString("srcDataId")
                ))
                .from(stats)
                .where(
                        path.getString("prdDe").eq(String.valueOf(fromYear)),
                        path.getDate("statLatestChnDt", java.util.Date.class).toString()
                                .equals(srcDataInfo.getStatLatestChnDt())
                )
                .orderBy(path.getString("prdDe").asc())
                .fetch();
    }

}
