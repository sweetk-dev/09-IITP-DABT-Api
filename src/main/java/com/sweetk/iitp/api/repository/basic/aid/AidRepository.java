package com.sweetk.iitp.api.repository.basic.aid;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AidRepository {
    // 보조기기 필요 현황
    List<StatDataItemDB> findAidDeviceNeedLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findAidDeviceNeedByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);

    // 보조기기 이용 현황
    List<StatDataItemDB> findAidDeviceUsageLatest(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
    List<StatDataItemDB> findAidDeviceUsageByYear(StatsSrcDataInfoEntity srcDataInfo, Integer targetYear);
} 