package com.sweetk.iitp.api.repository.basic.social.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.social.QStatsDisSocContactCntfreqEntity;
import com.sweetk.iitp.api.entity.basic.social.StatsDisSocContactCntfreqEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisSocContactCntfreqRepositoryImpl
        extends BasicQuerySupport<StatsDisSocContactCntfreqEntity>
        implements StatsDisSocContactCntfreqRepositoryCustom {

    private final QStatsDisSocContactCntfreqEntity qEntity = QStatsDisSocContactCntfreqEntity.statsDisSocContactCntfreqEntity;

    public StatsDisSocContactCntfreqRepositoryImpl(JPAQueryFactory queryFactory) {
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