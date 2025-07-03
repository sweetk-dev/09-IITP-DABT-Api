package com.sweetk.iitp.api.repository.basic.edu;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EduRepository {
    // 직업교육 이수 현황
    Integer getEduVocaExecLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEduVocaExecLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEduVocaExecByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 직업교육 이수 방법
    Integer getEduVocaExecWayLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEduVocaExecWayLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findEduVocaExecWayByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
} 