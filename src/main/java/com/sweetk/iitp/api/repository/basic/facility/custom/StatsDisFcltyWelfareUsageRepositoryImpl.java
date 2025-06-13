package com.sweetk.iitp.api.repository.basic.facility.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.facility.QStatsDisFcltyWelfareUsageEntity;
import com.sweetk.iitp.api.entity.basic.facility.StatsDisFcltyWelfareUsageEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisFcltyWelfareUsageRepositoryImpl
        extends BasicQuerySupport<StatsDisFcltyWelfareUsageEntity>
        implements StatsDisFcltyWelfareUsageRepositoryCustom {

    private final QStatsDisFcltyWelfareUsageEntity qEntity = QStatsDisFcltyWelfareUsageEntity.statsDisFcltyWelfareUsageEntity;

    public StatsDisFcltyWelfareUsageRepositoryImpl(JPAQueryFactory queryFactory) {
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