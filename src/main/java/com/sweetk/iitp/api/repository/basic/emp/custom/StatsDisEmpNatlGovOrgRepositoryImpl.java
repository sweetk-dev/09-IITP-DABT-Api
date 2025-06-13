package com.sweetk.iitp.api.repository.basic.emp.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.emp.QStatsDisEmpNatlGovOrgEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisEmpNatlGovOrgRepositoryImpl
        extends BasicQuerySupport <StatsDisEmpNatlGovOrgEntity>
        implements StatsDisEmpNatlGovOrgRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QStatsDisEmpNatlGovOrgEntity qEntity = QStatsDisEmpNatlGovOrgEntity.statsDisEmpNatlGovOrgEntity;

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return findLatestStats(qEntity, srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return findTargetStats(qEntity, srcDataInfo, targetYear);
    }
} 