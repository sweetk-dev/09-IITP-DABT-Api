package com.sweetk.iitp.api.repository.basic.health.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.health.QStatsDisHlthDiseaseCostSubEntity;
import com.sweetk.iitp.api.entity.basic.health.StatsDisHlthDiseaseCostSubEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisHlthDiseaseCostSubRepositoryImpl
        extends BasicQuerySupport <StatsDisHlthDiseaseCostSubEntity>
        implements StatsDisHlthDiseaseCostSubRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QStatsDisHlthDiseaseCostSubEntity qEntity = QStatsDisHlthDiseaseCostSubEntity.statsDisHlthDiseaseCostSubEntity;

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return findLatestStats(qEntity, srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return findTargetStats(qEntity, srcDataInfo, targetYear);
    }
} 