package com.sweetk.iitp.api.repository.basic.emp;

import com.sweetk.iitp.api.entity.basic.emp.StatsDisEmpNatlPublicEntity;
import com.sweetk.iitp.api.repository.basic.emp.custom.StatsDisEmpNatlPublicRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisEmpNatlPublicRepository extends JpaRepository<StatsDisEmpNatlPublicEntity, Long>, StatsDisEmpNatlPublicRepositoryCustom {
}