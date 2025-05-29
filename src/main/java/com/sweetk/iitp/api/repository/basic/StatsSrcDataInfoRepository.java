package com.sweetk.iitp.api.repository.basic;

import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsSrcDataInfoRepository extends JpaRepository<StatsSrcDataInfo, Integer> {
}