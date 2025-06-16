package com.sweetk.iitp.api.repository.basic.housing;


import com.sweetk.iitp.api.entity.basic.housing.StatsDisLifeSuppNeedLvlEntity;
import com.sweetk.iitp.api.repository.basic.housing.impl.StatsDisLifeSuppNeedLvlRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisLifeSuppNeedLvlRepository extends JpaRepository<StatsDisLifeSuppNeedLvlEntity, Long>, StatsDisLifeSuppNeedLvlRepositoryCustom {
}