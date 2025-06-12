package com.sweetk.iitp.api.repository.basic.health.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.health.QStatsDisHlthDiseaseCostSubEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupportDtString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisHlthDiseaseCostSubRepositoryImpl extends BasicQuerySupportDtString implements StatsDisHlthDiseaseCostSubRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        QStatsDisHlthDiseaseCostSubEntity qEntity = QStatsDisHlthDiseaseCostSubEntity.statsDisHlthDiseaseCostSubEntity;
        return buildLatestStatDataItemStringDtQuery(qEntity, srcDataInfo, fromYear).fetch();
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        QStatsDisHlthDiseaseCostSubEntity qEntity = QStatsDisHlthDiseaseCostSubEntity.statsDisHlthDiseaseCostSubEntity;
        return buildTargetStatDataItemStringDtQuery(qEntity, srcDataInfo, targetYear).fetch();
    }
} 