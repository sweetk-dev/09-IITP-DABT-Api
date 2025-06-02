package com.sweetk.iitp.api.repository.basic.edu;

import com.sweetk.iitp.api.entity.basic.edu.StatsDisEduVocaExec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisEduVocaExecRepository extends JpaRepository<StatsDisEduVocaExec, Integer> {
}