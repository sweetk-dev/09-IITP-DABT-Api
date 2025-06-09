package com.sweetk.iitp.api.repository.basic;

import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsSrcDataInfoRepository extends JpaRepository<StatsSrcDataInfoEntity, Integer> {
    StatsSrcDataInfoEntity findBySrcDataId(Integer srcDataId);
    StatsSrcDataInfoEntity findByIntgTblId(String intgTblId);

}