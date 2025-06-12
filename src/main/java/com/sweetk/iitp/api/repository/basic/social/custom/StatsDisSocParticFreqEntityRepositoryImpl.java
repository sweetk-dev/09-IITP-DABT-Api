package com.sweetk.iitp.api.repository.basic.social.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.social.QStatsDisSocParticFreqEntityEntity;
import com.sweetk.iitp.api.repository.basic.BasicRepositoryQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisSocParticFreqEntityRepositoryImpl extends BasicRepositoryQuerySupport implements StatsDisSocParticFreqEntityRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        QStatsDisSocParticFreqEntityEntity qEntity = StatsDisSocParticFreqEntityEntity.statsDisSocParticFreqEntity;
        return buildLatestStatDataItemQuery(qEntity, srcDataInfo, fromYear).fetch();
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        QStatsDisSocParticFreqEntityEntity qEntity = StatsDisSocParticFreqEntityEntity.statsDisSocParticFreqEntity;
        return buildTargetStatDataItemQuery(qEntity, srcDataInfo, targetYear).fetch();
    }
} 