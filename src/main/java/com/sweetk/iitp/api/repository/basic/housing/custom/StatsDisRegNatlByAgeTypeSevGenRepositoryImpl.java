package com.sweetk.iitp.api.repository.basic.housing.custom;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.QStatsDisRegNatlByAgeTypeSevGenEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupportDtString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisRegNatlByAgeTypeSevGenRepositoryImpl extends BasicQuerySupport implements StatsDisRegNatlByAgeTypeSevGenRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        QStatsDisRegNatlByAgeTypeSevGenEntity qEntity = QStatsDisRegNatlByAgeTypeSevGenEntity.statsDisRegNatlByAgeTypeSevGenEntity;
        return buildLatestStatDataItemQuery(qEntity, srcDataInfo, fromYear).fetch();
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        QStatsDisRegNatlByAgeTypeSevGenEntity qEntity = QStatsDisRegNatlByAgeTypeSevGenEntity.statsDisRegNatlByAgeTypeSevGenEntity;
        return buildTargetStatDataItemQuery(qEntity, srcDataInfo, targetYear).fetch();
    }
} 