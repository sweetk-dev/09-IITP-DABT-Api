package com.sweetk.iitp.api.repository.basic.house;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.QStatsDisRegNatlByNewEntity;
import com.sweetk.iitp.api.repository.basic.BasicRepositoryQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisRegNatlByNewRepositoryImpl extends BasicRepositoryQuerySupport implements StatsDisRegNatlByNewRepositoryCustom {
    
    private final JPAQueryFactory queryFactory;
    
    @Override
    public List<StatDataItemDB> findLatestRegNewData(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        QStatsDisRegNatlByNewEntity stats = QStatsDisRegNatlByNewEntity.statsDisRegNatlByNewEntity;
        return buildLatestStatDataItemQuery(stats, srcDataInfo, fromYear).fetch();
    }
} 