package com.sweetk.iitp.api.repository.basic.house;

import com.sweetk.iitp.api.entity.basic.house.StatsDisLifeSuppField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisLifeSuppFieldRepository extends JpaRepository<StatsDisLifeSuppField, Integer> {
}