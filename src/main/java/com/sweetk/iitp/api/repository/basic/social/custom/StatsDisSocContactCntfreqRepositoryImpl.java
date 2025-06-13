package com.sweetk.iitp.api.repository.basic.social.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.social.QStatsDisSocContactCntfreqEntity;
import com.sweetk.iitp.api.entity.basic.social.StatsDisSocContactCntfreqEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisSocContactCntfreqRepositoryImpl
        extends BasicQuerySupport <StatsDisSocContactCntfreqEntity>
        implements StatsDisSocContactCntfreqRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QStatsDisSocContactCntfreqEntity qEntity = QStatsDisSocContactCntfreqEntity.statsDisSocContactCntfreqEntity;


    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return findLatestStats(qEntity, srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return findTargetStats(qEntity, srcDataInfo, targetYear);
    }
} 