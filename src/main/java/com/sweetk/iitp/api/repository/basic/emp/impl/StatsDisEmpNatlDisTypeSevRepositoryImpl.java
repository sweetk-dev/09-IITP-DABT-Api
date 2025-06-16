package com.sweetk.iitp.api.repository.basic.emp.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.emp.QStatsDisEmpNatlDisTypeSevEntity;
import com.sweetk.iitp.api.entity.basic.emp.StatsDisEmpNatlDisTypeSevEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisEmpNatlDisTypeSevRepositoryImpl
        extends BasicQuerySupport <StatsDisEmpNatlDisTypeSevEntity>
        implements StatsDisEmpNatlDisTypeSevRepositoryCustom {

    //private final JPAQueryFactory queryFactory;
    private final QStatsDisEmpNatlDisTypeSevEntity qEntity = QStatsDisEmpNatlDisTypeSevEntity.statsDisEmpNatlDisTypeSevEntity;

    public StatsDisEmpNatlDisTypeSevRepositoryImpl(JPAQueryFactory queryFactory) {
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