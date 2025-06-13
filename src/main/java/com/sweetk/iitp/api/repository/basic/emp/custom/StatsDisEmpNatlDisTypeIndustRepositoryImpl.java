package com.sweetk.iitp.api.repository.basic.emp.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.emp.QStatsDisEmpNatlDisTypeIndustEntity;
import com.sweetk.iitp.api.entity.basic.emp.StatsDisEmpNatlDisTypeIndustEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisEmpNatlDisTypeIndustRepositoryImpl
        extends BasicQuerySupport <StatsDisEmpNatlDisTypeIndustEntity>
        implements StatsDisEmpNatlDisTypeIndustRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QStatsDisEmpNatlDisTypeIndustEntity qEntity = QStatsDisEmpNatlDisTypeIndustEntity.statsDisEmpNatlDisTypeIndustEntity;

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return findLatestStats(qEntity, srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return findTargetStats(qEntity, srcDataInfo, targetYear);
    }
} 