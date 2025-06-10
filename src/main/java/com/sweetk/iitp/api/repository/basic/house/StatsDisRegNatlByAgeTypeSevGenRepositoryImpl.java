package com.sweetk.iitp.api.repository.basic.house;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.QStatsDisRegNatlByAgeTypeSevGenEntity;
import com.sweetk.iitp.api.repository.basic.BasicRepositoryQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisRegNatlByAgeTypeSevGenRepositoryImpl extends BasicRepositoryQuerySupport implements StatsDisRegNatlByAgeTypeSevGenRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        QStatsDisRegNatlByAgeTypeSevGenEntity stats = QStatsDisRegNatlByAgeTypeSevGenEntity.statsDisRegNatlByAgeTypeSevGenEntity;
        return buildLatestStatDataItemQuery(stats, srcDataInfo, fromYear).fetch();
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        QStatsDisRegNatlByAgeTypeSevGenEntity stats = QStatsDisRegNatlByAgeTypeSevGenEntity.statsDisRegNatlByAgeTypeSevGenEntity;
        return buildTargetStatDataItemQuery(stats, srcDataInfo, targetYear).fetch();
    }
} 