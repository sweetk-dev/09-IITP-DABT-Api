package com.sweetk.iitp.api.repository.basic.health;

import com.sweetk.iitp.api.entity.basic.health.StatsDisHlthMedicalUsageEntity;
import com.sweetk.iitp.api.repository.basic.health.impl.StatsDisHlthMedicalUsageRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisHlthMedicalUsageRepository extends JpaRepository<StatsDisHlthMedicalUsageEntity, Long>, StatsDisHlthMedicalUsageRepositoryCustom {
}