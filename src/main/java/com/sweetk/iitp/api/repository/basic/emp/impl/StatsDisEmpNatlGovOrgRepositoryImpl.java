package com.sweetk.iitp.api.repository.basic.emp.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.emp.QStatsDisEmpNatlGovOrgEntity;
import com.sweetk.iitp.api.entity.basic.emp.StatsDisEmpNatlGovOrgEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisEmpNatlGovOrgRepositoryImpl
        extends BasicQuerySupport<StatsDisEmpNatlGovOrgEntity>
        implements StatsDisEmpNatlGovOrgRepositoryCustom {

    private final QStatsDisEmpNatlGovOrgEntity qEntity = QStatsDisEmpNatlGovOrgEntity.statsDisEmpNatlGovOrgEntity;

    public StatsDisEmpNatlGovOrgRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }


    @Override
    public Integer getDataLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear){
        return getLatestStatsCount(qEntity, srcDataInfo, fromYear, toYear);
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