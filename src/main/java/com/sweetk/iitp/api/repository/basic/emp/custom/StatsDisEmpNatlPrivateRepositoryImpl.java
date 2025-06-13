package com.sweetk.iitp.api.repository.basic.emp.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.emp.QStatsDisEmpNatlPrivateEntity;
import com.sweetk.iitp.api.entity.basic.emp.StatsDisEmpNatlPrivateEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisEmpNatlPrivateRepositoryImpl
        extends BasicQuerySupport<StatsDisEmpNatlPrivateEntity>
        implements StatsDisEmpNatlPrivateRepositoryCustom {

    private final QStatsDisEmpNatlPrivateEntity qEntity = QStatsDisEmpNatlPrivateEntity.statsDisEmpNatlPrivateEntity;

    public StatsDisEmpNatlPrivateRepositoryImpl(JPAQueryFactory queryFactory) {
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