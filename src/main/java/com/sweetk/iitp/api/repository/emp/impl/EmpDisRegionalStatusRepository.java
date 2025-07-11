package com.sweetk.iitp.api.repository.emp.impl;

import com.sweetk.iitp.api.entity.emp.EmpDisRegionalStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 지역별 장애인 고용 현황 리포지토리
 */
@Repository
public interface EmpDisRegionalStatusRepository extends JpaRepository<EmpDisRegionalStatusEntity, Integer> {
} 