package com.sweetk.iitp.api.repository.emp;

import com.sweetk.iitp.api.entity.emp.EmpDisDevSupportOrgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpDisDevSupportOrgRepository extends JpaRepository<EmpDisDevSupportOrgEntity, Integer> {
} 