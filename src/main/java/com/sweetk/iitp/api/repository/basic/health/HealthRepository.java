package com.sweetk.iitp.api.repository.basic.health;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRepository {
    // 건강체력운동 실시 유형
    List<StatDataItemDB> findHlthSportExecTypeLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findHlthSportExecTypeByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 건강의료이용
    List<StatDataItemDB> findHlthMedicalUsageLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findHlthMedicalUsageByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 건강운동보조기구
    List<StatDataItemDB> findHlthExrcBestAidLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findHlthExrcBestAidByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 건강질병비용
    List<StatDataItemDB> findHlthDiseaseCostSubLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findHlthDiseaseCostSubByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
} 