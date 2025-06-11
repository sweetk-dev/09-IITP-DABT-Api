package com.sweetk.iitp.api.repository.basic.aid.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.aid.QStatsDisAidDeviceNeedEntity;
import com.sweetk.iitp.api.repository.basic.BasicRepositoryQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisAidDeviceNeedRepositoryImpl extends BasicRepositoryQuerySupport implements StatsDisAidDeviceNeedRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        QStatsDisAidDeviceNeedEntity qEntity = QStatsDisAidDeviceNeedEntity.statsDisAidDeviceNeedEntity;
        return buildLatestStatDataItemQuery(qEntity, srcDataInfo, fromYear).fetch();
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        QStatsDisAidDeviceNeedEntity qEntity = QStatsDisAidDeviceNeedEntity.statsDisAidDeviceNeedEntity;
        return buildTargetStatDataItemQuery(qEntity, srcDataInfo, targetYear).fetch();
    }
} 