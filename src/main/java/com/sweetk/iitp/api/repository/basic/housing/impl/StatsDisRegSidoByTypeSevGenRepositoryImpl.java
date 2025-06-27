package com.sweetk.iitp.api.repository.basic.housing.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.QStatsDisRegSidoByTypeSevGenEntity;
import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegSidoByTypeSevGenEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisRegSidoByTypeSevGenRepositoryImpl
        extends BasicQuerySupport<StatsDisRegSidoByTypeSevGenEntity>
        implements StatsDisRegSidoByTypeSevGenRepositoryCustom {

    private final QStatsDisRegSidoByTypeSevGenEntity qEntity = QStatsDisRegSidoByTypeSevGenEntity.statsDisRegSidoByTypeSevGenEntity;

    public StatsDisRegSidoByTypeSevGenRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
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