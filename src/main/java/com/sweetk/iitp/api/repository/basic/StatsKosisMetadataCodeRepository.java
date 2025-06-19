package com.sweetk.iitp.api.repository.basic;

import com.sweetk.iitp.api.dto.internal.StatMetaCodeDB;
import com.sweetk.iitp.api.entity.basic.StatsKosisMetadataCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


interface StatsKosisMetadataCodeRepositoryCustom {
    List<StatMetaCodeDB> findItemInfoByCatObj(Integer srcDataId, LocalDate statLatestChnDt);
    List<StatMetaCodeDB> findItemInfoByItemObj(Integer srcDataId, LocalDate statLatestChnDt);
    List<StatMetaCodeDB> findMetaCodesByIds(Integer srcDataId, LocalDate statLatestChnDt, Set<String> itemIds);
}


@Repository
public interface StatsKosisMetadataCodeRepository extends JpaRepository<StatsKosisMetadataCodeEntity, Integer>,
        StatsKosisMetadataCodeRepositoryCustom {

}