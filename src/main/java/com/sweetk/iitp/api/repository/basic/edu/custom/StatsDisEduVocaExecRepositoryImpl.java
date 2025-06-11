package com.sweetk.iitp.api.repository.basic.edu.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.edu.QStatsDisEduVocaExecEntity;
import com.sweetk.iitp.api.repository.basic.BasicRepositoryQuerySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StatsDisEduVocaExecRepositoryImpl extends BasicRepositoryQuerySupport implements StatsDisEduVocaExecRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<StatDataItemDB> findDataLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        QStatsDisEduVocaExecEntity qEntity = QStatsDisEduVocaExecEntity.statsDisEduVocaExecEntity;
        return buildLatestStatDataItemQuery(qEntity, srcDataInfo, fromYear).fetch();
    }

    @Override
    public List<StatDataItemDB> findDataByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        QStatsDisEduVocaExecEntity qEntity = QStatsDisEduVocaExecEntity.statsDisEduVocaExecEntity;
        return buildTargetStatDataItemQuery(qEntity, srcDataInfo, targetYear).fetch();
    }
} 