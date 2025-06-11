package com.sweetk.iitp.api.repository.basic.edu;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EduRepositoryImpl implements EduRepository {
    private final StatsDisEduVocaExecRepository eduVocaExecRepos;
    private final StatsDisEduVocaExecWayRepository eduVocaExecWayRepos;

    @Override
    public List<StatDataItemDB> findEduVocaExecLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return eduVocaExecRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findEduVocaExecByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return eduVocaExecRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findEduVocaExecWayLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear) {
        return eduVocaExecWayRepos.findDataLatest(srcDataInfo, fromYear);
    }

    @Override
    public List<StatDataItemDB> findEduVocaExecWayByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return eduVocaExecWayRepos.findDataByYear(srcDataInfo, targetYear);
    }
} 