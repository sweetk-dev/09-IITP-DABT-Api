package com.sweetk.iitp.api.repository.basic.house;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import java.util.List;

public interface StatsDisRegNatlByNewRepositoryCustom {
    List<StatDataItemDB> findLatestRegNewData(com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
} 