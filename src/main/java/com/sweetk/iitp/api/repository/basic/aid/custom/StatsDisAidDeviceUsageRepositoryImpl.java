package com.sweetk.iitp.api.repository.basic.aid.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.aid.QStatsDisAidDeviceUsageEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import com.sweetk.iitp.api.repository.basic.aid.StatsDisAidDeviceUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisAidDeviceUsageRepositoryImpl
        extends BasicQuerySupport <StatsDisAidDeviceUsageRepository>
        implements StatsDisAidDeviceUsageRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QStatsDisAidDeviceUsageEntity qEntity = QStatsDisAidDeviceUsageEntity.statsDisAidDeviceUsageEntity;

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return findLatestStats(qEntity, srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        QStatsDisAidDeviceUsageEntity qEntity = QStatsDisAidDeviceUsageEntity.statsDisAidDeviceUsageEntity;
        return findTargetStats(qEntity, srcDataInfo, targetYear);
    }
} 