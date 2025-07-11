package com.sweetk.iitp.api.repository.emp.impl;

import com.sweetk.iitp.api.entity.emp.EmpDisStaffTrainCrsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpDisStaffTrainCrsRepository extends JpaRepository<EmpDisStaffTrainCrsEntity, Integer> {
} 