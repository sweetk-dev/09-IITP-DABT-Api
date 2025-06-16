package com.sweetk.iitp.api.repository.basic.housing;

import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegNatlByNewEntity;
import com.sweetk.iitp.api.repository.basic.housing.impl.StatsDisRegNatlByNewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisRegNatlByNewRepository extends JpaRepository<StatsDisRegNatlByNewEntity, Integer>,
        StatsDisRegNatlByNewRepositoryCustom {
    //List<StatDataItemDB> findLatestRegNewData(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
}