package com.sweetk.iitp.api.repository.basic.emp;

import com.sweetk.iitp.api.entity.basic.emp.StatsDisEmpNatlDisTypeIndustEntity;
import com.sweetk.iitp.api.repository.basic.emp.impl.StatsDisEmpNatlDisTypeIndustRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisEmpNatlDisTypeIndustRepository extends JpaRepository<StatsDisEmpNatlDisTypeIndustEntity, Long>, StatsDisEmpNatlDisTypeIndustRepositoryCustom {
}