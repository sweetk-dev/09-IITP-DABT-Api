package com.sweetk.iitp.api.repository.basic.house;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.BasicRepositoryQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisRegNatlByNewRepositoryImpl extends BasicRepositoryQuerySupport implements StatsDisRegNatlByNewRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<StatDataItemDB> findLatestRegNewData(StatsSrcDataInfoEntity srcDataInfo , Integer fromYear) {
        QBaseStatsEntity stats = QBaseStatsEntity.baseStatsEntity;

        List<StatDataItemDB> result = buildLatestStatDataItemQuery(stats,srcDataInfo, fromYear );

        return result;
    }


    @Override
    public List<StatDataItemDB> findTargetRegNewData(StatsSrcDataInfoEntity srcDataInfo , Integer targetYear) {
        QBaseStatsEntity stats = QBaseStatsEntity.baseStatsEntity;

        List<StatDataItemDB> result = buildTargetStatDataItemQuery(stats, srcDataInfo, targetYear );

        return result;
    }
} 