package com.sweetk.iitp.api.repository.basic.housing;

import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegSidoByTypeSevGenEntity;
import com.sweetk.iitp.api.repository.basic.housing.impl.StatsDisRegSidoByTypeSevGenRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisRegSidoByTypeSevGenRepository
        extends JpaRepository<StatsDisRegSidoByTypeSevGenEntity, Integer>,
        StatsDisRegSidoByTypeSevGenRepositoryCustom {
}