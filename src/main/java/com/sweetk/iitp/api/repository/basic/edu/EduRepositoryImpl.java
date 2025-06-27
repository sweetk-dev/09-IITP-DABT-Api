package com.sweetk.iitp.api.repository.basic.edu;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.repository.basic.edu.impl.StatsDisEduVocaExecRepository;
import com.sweetk.iitp.api.repository.basic.edu.impl.StatsDisEduVocaExecWayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EduRepositoryImpl implements EduRepository {
    private final StatsDisEduVocaExecRepository eduVocaExecRepos;
    private final StatsDisEduVocaExecWayRepository eduVocaExecWayRepos;

    @Override
    public List<StatDataItemDB> findEduVocaExecLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return eduVocaExecRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEduVocaExecByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return eduVocaExecRepos.findDataByYear(srcDataInfo, targetYear);
    }

    @Override
    public List<StatDataItemDB> findEduVocaExecWayLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear) {
        return eduVocaExecWayRepos.findDataLatest(srcDataInfo, fromYear, toYear);
    }

    @Override
    public List<StatDataItemDB> findEduVocaExecWayByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear) {
        return eduVocaExecWayRepos.findDataByYear(srcDataInfo, targetYear);
    }
} 