package com.sweetk.iitp.api.repository.basic.health;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRepository {
    // 건강체력운동 실시 유형
    Integer getHlthSportExecTypeLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findHlthSportExecTypeLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findHlthSportExecTypeByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 건강의료이용
    Integer getHlthMedicalUsageLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findHlthMedicalUsageLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findHlthMedicalUsageByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 건강운동보조기구
    Integer getHlthExrcBestAidLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findHlthExrcBestAidLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findHlthExrcBestAidByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 건강질병비용
    Integer getHlthDiseaseCostSubLatestCount (StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findHlthDiseaseCostSubLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear, Integer toYear);
    List<StatDataItemDB> findHlthDiseaseCostSubByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
} 