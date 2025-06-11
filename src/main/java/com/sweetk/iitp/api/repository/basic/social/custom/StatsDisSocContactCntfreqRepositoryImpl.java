package com.sweetk.iitp.api.repository.basic.social.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.social.QStatsDisSocContactCntfreqEntity;
import com.sweetk.iitp.api.repository.basic.BasicRepositoryQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisSocContactCntfreqRepositoryImpl extends BasicRepositoryQuerySupport implements StatsDisSocContactCntfreqRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        QStatsDisSocContactCntfreqEntity qEntity = QStatsDisSocContactCntfreqEntity.statsDisSocContactCntfreqEntity;
        return buildLatestStatDataItemQuery(qEntity, srcDataInfo, fromYear).fetch();
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        QStatsDisSocContactCntfreqEntity qEntity = QStatsDisSocContactCntfreqEntity.statsDisSocContactCntfreqEntity;
        return buildTargetStatDataItemQuery(qEntity, srcDataInfo, targetYear).fetch();
    }
} 