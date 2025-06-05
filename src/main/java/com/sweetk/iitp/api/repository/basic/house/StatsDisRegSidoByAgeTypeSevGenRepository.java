package com.sweetk.iitp.api.repository.basic.house;

import com.sweetk.iitp.api.entity.basic.housing.StatsDisRegSidoByAgeTypeSevGenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisRegSidoByAgeTypeSevGenRepository
        extends JpaRepository<StatsDisRegSidoByAgeTypeSevGenEntity, Integer> {
}