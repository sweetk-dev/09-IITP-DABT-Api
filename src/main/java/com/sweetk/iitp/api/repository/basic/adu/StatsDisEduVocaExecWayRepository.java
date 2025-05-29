package com.sweetk.iitp.api.repository.basic.adu;

import com.sweetk.iitp.api.entity.basic.adu.StatsDisEduVocaExecWay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisEduVocaExecWayRepository extends JpaRepository<StatsDisEduVocaExecWay, Integer> {
}