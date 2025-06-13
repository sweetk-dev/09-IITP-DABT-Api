package com.sweetk.iitp.api.repository.basic.housing.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.QStatsDisLifePrimcarerEntity;
import com.sweetk.iitp.api.entity.basic.housing.StatsDisLifePrimcarerEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisLifePrimcarerRepositoryImpl
        extends BasicQuerySupport<StatsDisLifePrimcarerEntity>
        implements StatsDisLifePrimcarerRepositoryCustom {

    private final QStatsDisLifePrimcarerEntity qEntity = QStatsDisLifePrimcarerEntity.statsDisLifePrimcarerEntity;

    public StatsDisLifePrimcarerRepositoryImpl(JPAQueryFactory queryFactory) {
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