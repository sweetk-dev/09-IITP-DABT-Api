package com.sweetk.iitp.api.repository.basic.social;

import com.sweetk.iitp.api.entity.basic.social.StatsDisSocContactCntfreq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisSocContactCntfreqRepository extends JpaRepository<StatsDisSocContactCntfreq, Integer> {
}