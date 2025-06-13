package com.sweetk.iitp.api.repository.basic.housing.custom;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.QStatsDisRegNatlByAgeTypeSevGenEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisRegNatlByAgeTypeSevGenRepositoryImpl
        extends BasicQuerySupport <StatsDisRegNatlByAgeTypeSevGenEntity>
        implements StatsDisRegNatlByAgeTypeSevGenRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QStatsDisRegNatlByAgeTypeSevGenEntity qEntity = QStatsDisRegNatlByAgeTypeSevGenEntity.statsDisRegNatlByAgeTypeSevGenEntity;

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return findLatestStats(qEntity, srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return findTargetStats(qEntity, srcDataInfo, targetYear);
    }
} 