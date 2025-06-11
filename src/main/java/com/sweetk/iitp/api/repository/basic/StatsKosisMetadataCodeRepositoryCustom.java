package com.sweetk.iitp.api.repository.basic;

import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface StatsKosisMetadataCodeRepositoryCustom {
    List<StatMetaCodeDB> findItemInfoByCatObj(Integer srcDataId, LocalDate statLatestChnDt);
    List<StatMetaCodeDB> findItemInfoByItemObj(Integer srcDataId, LocalDate statLatestChnDt);
    List<StatMetaCodeDB> findMetaCodesByIds(Integer srcDataId, LocalDate statLatestChnDt, Set<String> itemIds);
}
