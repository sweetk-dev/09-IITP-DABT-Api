package com.sweetk.iitp.api.repository.basic.social;

import com.sweetk.iitp.api.entity.basic.social.StatsDisSocParticFreqEntity;
import com.sweetk.iitp.api.repository.basic.social.impl.StatsDisSocParticFreqRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisSocParticFreqRepository
        extends JpaRepository<StatsDisSocParticFreqEntity, Integer>,
        StatsDisSocParticFreqRepositoryCustom {
}