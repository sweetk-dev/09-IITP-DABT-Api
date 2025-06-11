package com.sweetk.iitp.api.repository.basic.housing.custom;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.QStatsDisRegSidoByTypeSevGenEntity;
import com.sweetk.iitp.api.repository.basic.BasicRepositoryQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisRegSidoByTypeSevGenRepositoryImpl extends BasicRepositoryQuerySupport implements StatsDisRegSidoByTypeSevGenRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        QStatsDisRegSidoByTypeSevGenEntity qEntity = QStatsDisRegSidoByTypeSevGenEntity.statsDisRegSidoByTypeSevGenEntity;
        return buildLatestStatDataItemQuery(qEntity, srcDataInfo, fromYear).fetch();
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        QStatsDisRegSidoByTypeSevGenEntity qEntity = QStatsDisRegSidoByTypeSevGenEntity.statsDisRegSidoByTypeSevGenEntity;
        return buildTargetStatDataItemQuery(qEntity, srcDataInfo, targetYear).fetch();
    }
} 