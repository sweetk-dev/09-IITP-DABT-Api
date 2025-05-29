package com.sweetk.iitp.api.repository.basic.house;

import com.sweetk.iitp.api.entity.basic.house.StatsDisRegNatlByNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisRegNatlByNewRepository extends JpaRepository<StatsDisRegNatlByNew, Integer> {
}