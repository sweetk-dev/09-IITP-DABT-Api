package com.sweetk.iitp.api.repository.basic.housing.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.QStatsDisRegNatlByAgeTypeSevGenEntity;
import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegNatlByAgeTypeSevGenEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisRegNatlByAgeTypeSevGenRepositoryImpl
        extends BasicQuerySupport<StatsDisRegNatlByAgeTypeSevGenEntity>
        implements StatsDisRegNatlByAgeTypeSevGenRepositoryCustom {

    private final QStatsDisRegNatlByAgeTypeSevGenEntity qEntity = QStatsDisRegNatlByAgeTypeSevGenEntity.statsDisRegNatlByAgeTypeSevGenEntity;

    public StatsDisRegNatlByAgeTypeSevGenRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return findLatestStats(qEntity, srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return findTargetStats(qEntity, srcDataInfo, targetYear);
    }
} 