package com.sweetk.iitp.api.repository.basic.emp;

import com.sweetk.iitp.api.entity.basic.emp.StatsDisEmpNatlGovOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsDisEmpNatlGovOrgRepository extends JpaRepository<StatsDisEmpNatlGovOrg, Integer> {
}