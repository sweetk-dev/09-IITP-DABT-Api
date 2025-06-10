package com.sweetk.iitp.api.repository.basic;

import com.sweetk.iitp.api.entity.basic.StatsKosisMetadataCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsKosisMetadataCodeRepository extends JpaRepository<StatsKosisMetadataCodeEntity, Integer>,
        StatsKosisMetadataCodeRepositoryCustom {

}