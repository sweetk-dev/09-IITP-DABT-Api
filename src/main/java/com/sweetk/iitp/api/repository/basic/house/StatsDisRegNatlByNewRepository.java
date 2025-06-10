package com.sweetk.iitp.api.repository.basic.house;

import com.sweetk.iitp.api.dto.internal.StatDataItemDB;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegNatlByNewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatsDisRegNatlByNewRepository extends JpaRepository<StatsDisRegNatlByNewEntity, Integer>, StatsDisRegNatlByNewRepositoryCustom {
    List<StatDataItemDB> findLatestRegNewData(StatsSrcDataInfoEntity srcDataInfo, Integer fromYear);
}