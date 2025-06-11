package com.sweetk.iitp.api.repository.basic.housing;

import com.sweetk.iitp.api.entity.basic.housing.StatsDisLifeSuppFieldEntity;
import com.sweetk.iitp.api.repository.basic.housing.custom.StatsDisLifeSuppFieldRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisLifeSuppFieldRepository extends JpaRepository<StatsDisLifeSuppFieldEntity, Integer>,
        StatsDisLifeSuppFieldRepositoryCustom {
}