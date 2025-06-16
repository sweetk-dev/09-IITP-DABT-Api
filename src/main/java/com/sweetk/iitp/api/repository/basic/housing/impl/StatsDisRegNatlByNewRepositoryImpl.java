package com.sweetk.iitp.api.repository.basic.housing.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.QStatsDisRegNatlByNewEntity;
import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegNatlByNewEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisRegNatlByNewRepositoryImpl
        extends BasicQuerySupport<StatsDisRegNatlByNewEntity>
        implements StatsDisRegNatlByNewRepositoryCustom {

    private final QStatsDisRegNatlByNewEntity qEntity = QStatsDisRegNatlByNewEntity.statsDisRegNatlByNewEntity;

    public StatsDisRegNatlByNewRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return findLatestStats(qEntity, srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return findTargetStats(qEntity, srcDataInfo, targetYear);
    }
} 