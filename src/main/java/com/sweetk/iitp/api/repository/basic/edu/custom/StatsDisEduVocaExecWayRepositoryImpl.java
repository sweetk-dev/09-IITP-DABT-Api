package com.sweetk.iitp.api.repository.basic.edu.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.edu.QStatsDisEduVocaExecWayEntity;
import com.sweetk.iitp.api.entity.basic.edu.StatsDisEduVocaExecWayEntity;
import com.sweetk.iitp.api.repository.basic.BasicQuerySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatsDisEduVocaExecWayRepositoryImpl
        extends BasicQuerySupport<StatsDisEduVocaExecWayEntity>
        implements StatsDisEduVocaExecWayRepositoryCustom {

    private final QStatsDisEduVocaExecWayEntity qEntity = QStatsDisEduVocaExecWayEntity.statsDisEduVocaExecWayEntity;

    public StatsDisEduVocaExecWayRepositoryImpl(JPAQueryFactory queryFactory) {
        super(queryFactory);
    }

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return findLatestStats(qEntity, srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return findTargetStats(qEntity, srcDataInfo, targetYear);
    }
} 