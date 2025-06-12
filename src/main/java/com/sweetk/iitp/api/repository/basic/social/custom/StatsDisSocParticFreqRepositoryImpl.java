package com.sweetk.iitp.api.repository.basic.social.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.social.QStatsDisSocParticFreqEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisSocParticFreqRepositoryImpl extends BasicQuerySupport implements StatsDisSocParticFreqRepositoryCustom {
    private final QStatsDisSocParticFreqEntity qEntity = QStatsDisSocParticFreqEntity.statsDisSocParticFreqEntity;

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return getLatestStats(qEntity, srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return getTargetStats(qEntity, srcDataInfo, targetYear);
    }
} 