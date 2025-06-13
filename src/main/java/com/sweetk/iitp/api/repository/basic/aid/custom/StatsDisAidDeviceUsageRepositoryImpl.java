package com.sweetk.iitp.api.repository.basic.aid.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.aid.QStatsDisAidDeviceUsageEntity;
import com.sweetk.iitp.api.entity.basic.aid.StatsDisAidDeviceUsageEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import com.sweetk.iitp.api.repository.basic.aid.StatsDisAidDeviceUsageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisAidDeviceUsageRepositoryImpl
        extends BasicQuerySupport<StatsDisAidDeviceUsageEntity>
        implements StatsDisAidDeviceUsageRepositoryCustom {

    private final QStatsDisAidDeviceUsageEntity qEntity = QStatsDisAidDeviceUsageEntity.statsDisAidDeviceUsageEntity;

    public StatsDisAidDeviceUsageRepositoryImpl(JPAQueryFactory queryFactory) {
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